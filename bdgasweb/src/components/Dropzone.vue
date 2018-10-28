/*************************************************** 
 Dropzone.vue component
 Courtesy of kfei under MIT license
 Available at: https://github.com/kfei/vue-s3-dropzone
 ********************************************************
*/

<template>
  <form class="dropzone">
    <!-- Not displayed, just for Dropzone's `dictDefaultMessage` option -->
    <div id="dropzone-message" style="display: none">
      <span class="dropzone-title">Drop files here or click to select</span>
      <span class="dropzone-info"></span>
    </div>
  </form>
</template>

<script>
// Large file multi-part upload logic courtesy of:
// https://datalanguage.com/news/s3-managed-uploads

import S3 from 'aws-sdk/clients/s3';
import AWS from 'aws-sdk/global';

import Dropzone from 'dropzone'
import '../../node_modules/dropzone/dist/dropzone.css'

import awsservice from '../services/awsservice'
import datasourceservice from '../services/datasourceservice';

import config from '../config'

function acceptCallback(file, done) {
  // options for the managed upload for this accepted file
  // define the bucket, and the S3 key the file will be stored as
  var params = {Bucket: config.BUCKETNAME, Key: file.name, Body: file};
  // add an S3 managed upload instance to the file
  file.s3upload = new S3.ManagedUpload({params: params});
  done();
}

// override the uploadFiles function to send via AWS SDK instead of xhr
Dropzone.prototype.uploadFiles = function (files) {
  for (var j = 0; j < files.length; j++) {
    var file = files[j];
    sendFile(file);
  }
};

function sendFile(file) {
  file.s3upload.send(function(err, data) {
    if (err) {
      dropzone.emit("error", file, err.message);
    } else {
      dropzone.emit("complete", file);
    }
  });

  // listen to the AWS httpUploadProgress event, and emit an equivalent Dropzone event
  file.s3upload.on('httpUploadProgress', function(progress) {
    if (progress.total) {
      var percent = ((progress.loaded * 100) / progress.total);
      dropzone.emit('uploadprogress', file, percent, progress.loaded);
    }
  });
}

function abortUpload(file) {
  if (file.s3upload) file.s3upload.abort();
}

Dropzone.autoDiscover = false
export default {
  name: 'dropzone', 
  mounted () {

    const vm = this
    let options = {
      // The URL will be changed for each new file being processing
      url: '/',
      // Since we're going to do a `PUT` upload to S3 directly
      method: 'put',
      //F. Botha Edit to allow files to be split up:
      // chunking: true,
      // retryChunks: true,
      timeout: 3600000,
      maxFilesize: 30000,
      // Hijack the xhr.send since Dropzone always upload file by using formData
      // ref: https://github.com/danialfarid/ng-file-upload/issues/743
      sending (file, xhr) {
        let _send = xhr.send
        xhr.send = () => {
          _send.call(xhr, file)
        }
      },
      // Upload one file at a time since we're using the S3 pre-signed URL scenario
      parallelUploads: 1,
      uploadMultiple: false,
      // Content-Type should be included, otherwise you'll get a signature
      // mismatch error from S3. We're going to update this for each file.
      headers: "{}",
      // Customize the wording
      dictDefaultMessage: document.querySelector('#dropzone-message').innerHTML,
      // We're going to process each file manually (see `accept` below)
      autoProcessQueue: false,
      accept: acceptCallback,
      canceled : abortUpload,
      init: function() {
        this.on("success", function(file) { 
          console.dir(file);
          console.log('Adding DataSource...')
          datasourceservice.addDataSource(file);
        });
        this.on('removedfile', function (file) {
          abortUpload(file)
        })
      }
    }

    var idToken =  this.$store.getters.accessToken; // get from local storage/session
    AWS.config.credentials = new AWS.WebIdentityCredentials({
        RoleArn: 'arn:aws:iam::969323882038:role/OpenIdS3Role',
        WebIdentityToken: idToken    
    });

    // Instantiate Dropzone
    this.dropzone = new Dropzone(this.$el, options)

      // Set signed upload URL for each file
      vm.dropzone.on('processing', (file) => {
        vm.dropzone.options.headers = {"Access-Control-Allow-Origin": "*",
                                        "Cache-Control": "",
                                        "Access-Control-Allow-Methods": "POST, PUT, GET",
                                        "Access-Control-Allow-Headers": "*",
                                        "Content-Type": file.type}
      })
  }
}
</script>

<style lang="stylus">
primaryBlue = #3498db
form.dropzone
  transition all 0.2s linear
  border 2px dashed #ccc
  background-color #fafafa
  min-height initial
  &:hover
    border-color primaryBlue
    background-color white
    .dz-message
      .dropzone-title
        color primaryBlue
  .dz-message
    color #666
    span
      line-height 1.8
      font-size 13px
      letter-spacing 0.4px
      span.dropzone-title
        display block
        color #888
        font-size 1.25em
      span.dropzone-info
        display block
        color #a8a8a8
</style>
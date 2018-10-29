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
          <!-- Modal Component -->
          <b-modal v-model="displayAlert"
                  id="alertModal" 
                  title="Done"
                  header-bg-variant='dark'
                  header-text-variant='light'
                  body-bg-variant='light'
                  ok-title='OK'
                  ok-variant='success'
                  ok-only=true
                  :no-close-on-backdrop='true'
                  ><p class="my-4">The upload is complete.</p>
        </b-modal>
      </form>
</template>

<script>
// Large file multi-part upload logic courtesy of:
// https://datalanguage.com/news/s3-managed-uploads

import AWS from '../../node_modules/aws-sdk/global';
import S3 from '../../node_modules/aws-sdk/clients/s3';

import Dropzone from 'dropzone'
import '../../node_modules/dropzone/dist/dropzone.css'

//import awsservice from '../services/awsservice'
import datasourceservice from '../services/datasourceservice';

import config from '../config'

Dropzone.autoDiscover = false
export default {
  name: 'dropzone', 
  methods: {
      showAlert () {
        this.displayAlert = true
      },   
  },  
  data () {
      return {
        displayAlert: false,
      }
  },   
  mounted () {

    const vm = this
    var cancelled = false;
    var idToken =  this.$store.getters.accessToken; // get from local storage/session

    AWS.config.credentials = new AWS.WebIdentityCredentials({
        RoleArn: 'arn:aws:iam::969323882038:role/OpenIdS3Role',
        WebIdentityToken: idToken
    });

    AWS.config.region = config.AWSREGION ;

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
          console.log("Error in send file...")
          console.dir(err)
          vm.dropzone.emit("error", file, err.message);
        } else {
          vm.dropzone.emit("complete", file);
        }
      });

      // listen to the AWS httpUploadProgress event, and emit an equivalent Dropzone event
      file.s3upload.on('httpUploadProgress', function(progress) {
        if (progress.total) {
          var percent = ((progress.loaded * 100) / progress.total);
          vm.dropzone.emit('uploadprogress', file, percent, progress.loaded);
        }
      });
    }

    function abortUpload(file) {
      if (file.s3upload) file.s3upload.abort();
      cancelled = true;
    }    

    let options = {
      timeout: 9999999999999,
      maxFilesize: 300000,
      url: '/',
      addRemoveLinks: true,      
      accept: acceptCallback,
      canceled : abortUpload,
      init: function() {
        this.on("complete", function(file) { 
          if (cancelled == false) {
            console.log('Adding DataSource...')
            datasourceservice.addDataSource(file);
            vm.dropzone.removeFile(file);
            vm.showAlert()    
            cancelled = false;
          }      
        });
        this.on('removedfile', function (file) {
          abortUpload(file)
        })
      }      
    }

    // Instantiate Dropzone
    this.dropzone = new Dropzone(this.$el, options)

    // Set signed upload URL for each file
    vm.dropzone.on('processing', (file) => {
      console.log("processing...")
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
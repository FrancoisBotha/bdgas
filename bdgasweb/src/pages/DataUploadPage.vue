<template>
  <div>
      {{ signedS3Url}}
      <div class="row">
        <div class="float-left p-3">
            <button type="button" @click="getSignedS3Url()" class="btn btn-dark">New Upload</button>
        </div>
      </div>
      <dropzone></dropzone>
  </div>
</template>

<script>
  import Dropzone from '../components/Dropzone.vue'

  export default {
    components: {
      Dropzone
    },
    computed: {
        signedS3Url() {
            return this.$store.getters.signedS3Url;
        }
    },
    methods: {
        getSignedS3Url() {
            this.$store.dispatch('fetchSignedS3Url');
        }
    }
  }
</script>

<style lang="stylus">
body
  font-family 'Open Sans', sans-serif
</style>
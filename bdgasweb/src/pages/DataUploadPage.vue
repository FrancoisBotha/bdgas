<template>
  <div class="mt-3">
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
    },
    methods: {
    }
  }
</script>

<style lang="stylus">
body
  font-family 'Open Sans', sans-serif
</style>
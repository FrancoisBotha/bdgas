<template>
<div class="mt-3 col-md-8">
  <b-card header="Help"
          header-text-variant="white"
          header-tag="header"
          header-bg-variant="dark"
          style="max-width: 100%;"
          class="zeroPadding"
  >
    <div class="card-body text-dark bg-light zeroPadding">
       <textarea class="form-control bg-light helpTextDisplay" 
                name="txt" 
                rows="11" 
                readonly
                v-model="helpTextDataSection"></textarea>
    </div>
  </b-card>
</div>
</template>

<script>
  export default {
    computed: {
      helpTextDataSection() {
        return this.$store.getters.helpText("DataSection").txt
      }
    }
  }
</script>

<style>
  .helpTextDisplay {
      -webkit-box-shadow: none!important;
      box-shadow: none;
  }

  .zeroPadding {
      padding: 0; 
  }

  .card-body {
      padding: 0; 
  }
</style>

<template>
    <div>
        <textarea class="form-control bg-light helpTextDisplay" 
            name="txt" 
            rows="8" 
            readonly
            v-model="helpTextAuditSection"></textarea> 
    </div>
</template>

<script>
  export default {
    computed: {
      helpTextAuditSection() {
        return this.$store.getters.helpText("AuditSection").txt
      }
    }
  }
</script>

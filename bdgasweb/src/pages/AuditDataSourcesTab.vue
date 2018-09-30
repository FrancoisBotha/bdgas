<template>
    <div>
        <div class="ml-1 mt-1 text-left">
          <h5>Data Sources:</h5>
        </div>
        <form>
          <div class="form-group row">
            <label for="primaryDataSource" class="col-sm-3 col-form-label">Primary Data Source:</label>
            <div class="col-sm-9">
              <input type="text" readonly class="form-control-plaintext" id="primaryDataSource" :value="dataSource">
            </div>
          </div>
      </form>
    </div>
</template>

<script>
  export default {
    computed: {
      helpTextAuditSection() {
        return this.$store.getters.activeHelpText
      },
      dataSource() {
        return this.$store.getters.selectedPrimaryDataSource
               + "  ("
               + this.$store.getters.selectedPrimaryDataAlias
               + ")"
      },
      selectedPrimaryDataSource() {
        return this.$store.getters.selectedPrimaryDataSource
      },
      selectedPrimaryDataAlias() {
        return this.$store.getters.selectedPrimaryDataAlias
      }
    }
  }
</script>

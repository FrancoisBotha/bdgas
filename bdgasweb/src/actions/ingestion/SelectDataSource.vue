<template>
    <div>
        <div class="ml-1 mt-1 text-left">
          <h5>Select Data Source:</h5>
        </div>
        <div class="ml-1">
            <b-form inline>
                <label class="mr-sm-2" for="selectDataSource">Data Source:</label>
                <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                                required
                                v-model="datasource"
                                id="selectDataSource"
                                 @input="selectOption()">
                                <option slot="first" :value="null">Choose...</option>
                                <option v-for="ads in auditDataSources" 
                                        :key="ads.id"
                                        :value="ads.fileName">{{ ads.fileName }}</option>
                </b-form-select>
            </b-form>
            <br><br><br><br>
            
        </div>
    </div>
</template>
<script>
export default {
  data () {
    return {
      datasource: null
    }
  },
  computed: {
    auditDataSources() {
        return this.$store.getters.auditDataSources
    }
  },  
  methods: {
    selectOption() {
      let payload = {
        i: 0,
        parameter: this.datasource
      }
      this.$store.dispatch('setParameter', payload)
    } 
  },
  created: function () {
    if (this.$store.getters.localMode) {
      //Local mode is enabled, use local data sources
      this.$store.dispatch('setAuditDataSources', this.$store.getters.localDataSources)
    } else {
      //...use cloud data sources
      this.$store.dispatch('setAuditDataSources', this.$store.getters.dataSources)
    }
  },
}
</script>

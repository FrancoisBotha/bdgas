<template>
    <div>
        <div class="ml-1 mt-2 text-left">
          <h5>Select Data Source:</h5>
        </div>
          <div class="form-group row">
            <label for="selectDataSource" class="col-sm-3 col-form-label">Data Source:</label>
            <div class="col-sm-5">
                <b-form-select class=""
                              required
                              v-model="datasource"
                              id="selectDataSource"
                              @input="selectOption()">
                              <option slot="first" :value="null">Choose...</option>
                              <option v-for="ads in auditDataSources" 
                                      :key="ads.id"
                                      :value="ads.fileName">{{ ads.fileName }}</option>
                </b-form-select>          
            </div>
          </div>
          <div class="form-group row mt-0">
            <label for="selectDelimiter" class="col-sm-3 col-form-label">Delimiter:</label>
            <div class="col-sm-5">
              <b-form-select class=""
                            required
                            v-model="datasource"
                            id="selectDelimiter"
                            @input="selectOption()">
                            <option slot="first" :value="null">Choose...</option>
                            <option v-for="ads in auditDataSources" 
                                    :key="ads.id"
                                    :value="ads.fileName">{{ ads.fileName }}</option>
            </b-form-select>            
            </div>
          </div>
          <div class="form-group row mt-0">
            <label for="inputAlias" class="col-sm-3 col-form-label">Alias:</label>
            <div class="col-sm-5">
                <b-form-input v-model="text1"
                    type="text"
                    id="inputAlias"
                    placeholder="Alias for selected file">
                </b-form-input>            
            </div>
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

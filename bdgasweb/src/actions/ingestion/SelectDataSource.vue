<template>
    <div>
        <div class="ml-1 mt-2 text-left">
          <h5>{{activeActionTitle}}:</h5>
        </div>
          <div class="form-group row">
            <label for="selectDataSource" class="col-sm-3 col-form-label text-right">Data Source:</label>
            <div class="col-sm-5">
                <b-form-select class=""
                              required
                              v-model="parameter0"
                              id="selectDataSource"
                              @input="selectOption0()">
                              <option slot="first" :value="null">Choose...</option>
                              <option v-for="ads in auditDataSources" 
                                      :key="ads.id"
                                      :value="ads.fileName">{{ ads.fileName }}</option>
                </b-form-select>          
            </div>
          </div>
          <div class="form-group row mt-0">
            <label for="selectDelimiter" class="col-sm-3 col-form-label text-right">Delimiter:</label>
            <div class="col-sm-5">
              <b-form-select class=""
                            required
                            v-model="parameter1"
                            id="selectDelimiter"
                            @input="selectOption1()">
                            <option :value="null">Choose...</option>
                            <option v-for="delim in delimiters" 
                                    :key="delim.id"
                                    :value="delim.cde">{{ delim.cdeDesc }}</option>
              </b-form-select>            
            </div>
          </div>
          <div class="form-group row mt-0">
            <label for="inputAlias" class="col-sm-3 col-form-label text-right">Alias:</label>
            <div class="col-sm-5">
                <b-form-input v-model="parameter2"
                    type="text"
                    id="inputAlias"
                    placeholder="Alias for selected file">
                </b-form-input>            
            </div>
          </div>
    </div>
</template>
<script>
import {_} from 'vue-underscore';
import config from '../../config'

export default {
  data () {
    return {
      parameter0: null,
      parameter1: null,
      parameter2: null
    }
  },
  watch: {
    // Watch, in a debounced manner, changes to parameter 2
   parameter2: function() {
     this.debouncedParam2()
   } 
  },  
  computed: {
    auditDataSources() {
        return this.$store.getters.auditDataSources
    },
    delimiters() {
      return this.$store.getters.delimiters
    },
    activeActionTitle() {
      return this.$store.getters.activeActionTitle
    }
  },  
  methods: {
    selectOption0() {
      var file = "";
      if (this.$store.getters.localMode) {
        file = this.parameter0;
      } else {
        file = "s3a://" + config.BUCKETNAME + "/" + this.parameter0;
      }
      let payload = {
        i: 0,
        parameter: file
      }
      this.$store.dispatch('setParameter', payload)
    },
    selectOption1() {
      let payload = {
        i: 1,
        parameter: this.parameter1
      }
      this.$store.dispatch('setParameter', payload)
    },
    selectOption2() {
      let payload = {
        i: 2,
        parameter: this.parameter2
      }
      this.$store.dispatch('setParameter', payload)
    }        
  },
  created: function () {
    this.debouncedParam2 = _.debounce(this.selectOption2, 500)

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

<style>
.form-group {
    margin-bottom: 0.3rem;
}
</style>




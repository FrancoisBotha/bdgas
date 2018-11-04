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
          <br>
          <br><br>
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
  },  
  computed: {
    auditDataSources() {
        return this.$store.getters.auditDataSources
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
    }     
  },
  created: function () {
    let payload1 = {
      i: 1,
      parameter: "none"
    }
    this.$store.dispatch('setParameter', payload1)
    let payload2 = {
      i: 2,
      parameter: "none"
    }
    this.$store.dispatch('setParameter', payload2)

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




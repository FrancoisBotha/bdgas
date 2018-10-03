<template>
    <div>
        <div class="ml-1 mt-2 text-left">
          <h5>{{activeActionTitle}}:</h5>
        </div>
          <div class="form-group row">          
            <label for="primaryDataSource" class="col-sm-3 col-form-label text-right">Data Source:</label>
            <div class="col-sm-8">
              <input type="text" readonly class="form-control-plaintext" id="primaryDataSource" :value="dataSource">        
            </div>      
          </div>
          <div class="form-group row mt-0">
            <label for="selectColumn" class="col-sm-3 col-form-label text-right">Column:</label>
            <div class="col-sm-5">
              <b-form-select class=""
                            required
                            v-model="parameter2"
                            id="selectColumn"
                            @input="selectOption2()">
                            <option :value="null">Choose...</option>
                            <option v-for="schema in primaryNumericSchema" 
                                    :key="schema.col_name"
                                    :value="schema.col_name">{{ schema.col_name }}</option>
              </b-form-select>            
            </div>
          </div>
          <br><br>
    </div>
</template>
<script>
import {_} from 'vue-underscore';

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
    activeActionTitle() {
      return this.$store.getters.activeActionTitle
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
    },
    primaryNumericSchema() {
      return this.$store.getters.primaryNumericSchema
    }    
  },  
  methods: {
    selectOption2() {
      let payload2 = {
        i: 2,
        parameter: this.parameter2
      }
      this.$store.dispatch('setParameter', payload2)
    }        
  },
  created: function () {
    this.debouncedParam2 = _.debounce(this.selectOption2, 500)

    let payload0 = {
        i: 0,
        parameter: this.selectedPrimaryDataSource
    }  
    this.$store.dispatch('setParameter', payload0)

    let payload1 = {
        i: 1,
        parameter: this.selectedPrimaryDataAlias
      }  
    this.$store.dispatch('setParameter', payload1)
  },
}
</script>


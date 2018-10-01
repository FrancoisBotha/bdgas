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
            <label for="inputRows" class="col-sm-3 col-form-label text-right">Percentage:</label>
            <div class="col-sm-2">
                <b-form-input v-model="parameter2"
                    type="text"
                    id="inputRows">
                </b-form-input>            
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
    // Watch, in a debounced manner, changes to parameter 2
   parameter2: function() {
     this.debouncedParam2()
   } 
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

<style>
.form-group {
    margin-bottom: 0.3rem;
}
</style>

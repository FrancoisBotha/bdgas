<template>
  <div class="text-left">
    <div class="row">
      <div class="col-3">
        <div class="card text-white bg-dark mb-3 mt-1 d-flex ">
          <div class="card-header mt-1 ">
            <div class="mr-auto">Action </div>            
          </div>
          <div class="card-body text-dark bg-white" style="height:210px; padding: 0.25rem;">
              <div>
                <b-dropdown id="cbxdi" text="Data Ingestion" class="ml-0 mb-2">
                  <b-dropdown-item>Select Data Source</b-dropdown-item>
                </b-dropdown>
              </div>
              <div>
                <b-dropdown id="cbxct" text="Core Tests" class="ml-0 mb-2">
                  <b-dropdown-item-button  v-for="ct in coreTests" v-on:click="test">{{ ct.menuDesc }}</b-dropdown-item-button>
                </b-dropdown>
              </div>
              <div>
                <b-dropdown id="cbxpt" text="Plugin Tests" class="ml-0 mb-2">
                  <b-dropdown-item-button>Word Count</b-dropdown-item-button>
                </b-dropdown>
              </div>              
              <div>
                <b-dropdown id="cbxdd" text="Data Display" class="ml-0 mb-2">
                  <b-dropdown-item-button>Show Data</b-dropdown-item-button>
                </b-dropdown>
              </div>   
          </div>
        </div>
      </div>
      <div class="col-9 ml-0 mt-1" style="padding-left: 1px;">
        <b-card no-body  class="text-white bg-dark"  style="height:264px" >
          <b-tabs card >
            <b-tab title="Parameters" >
              <div class="bg-light text-dark" style="height: auto">
                  Tab Contents 1111
              </div>
            </b-tab>
            <b-tab title="Help" active  class="bg-white text-dark">
              <div class="card-body text-dark bg-light zeroPadding">
                <!-- <textarea class="form-control bg-light helpTextDisplay" 
                          name="txt" 
                          rows="8" 
                          readonly
                          v-model="helpTextAuditSection"></textarea> -->
              </div>
            </b-tab>
          </b-tabs>
        </b-card>
      </div>
    </div>
    <div>
      <app-wpline v-for="ln in orderedWPLines" :wpline="ln"></app-wpline>
    </div>
  </div>
</template>

<script>

  import WPLine from '../components/WPLine.vue'
  import {_} from 'vue-underscore';

  export default {
    components: {
        appWpline: WPLine,
    },      
    data () {
      return {
        selected: null,
        coreTests: [
          { taskCde: '2000100', menuDesc: 'Sample' },
          { taskCde: '2000200', menuDesc: 'Find Gaps' },
          { taskCde: '2000300', menuDesc: 'Find Duplicates' },
          { taskCde: '2000400', menuDesc: 'Recalc Column' }
        ],
        wplines: [
          {
            lnNo: 1,
            taskCde: "1000100",
            taskDesc: "Select Data Source",
            taskParams: "/airticketdata.csv",
            lnResult: "",
            lnState: "Complete"
          },
          {
            lnNo: 2,
            taskCde: "2000200",
            taskDesc: "Find Gaps",
            taskParams: "",
            lnResult: "",
            lnState: "Complete"
          },
          {
            lnNo: 3,
            taskCde: "4000100",
            taskDesc: "Show Data",
            taskParams: "",
            lnResult: "",
            lnState: "Complete"
          },
        ]
      }
    },    
    methods: {
      test: function() {
        alert("this")
      }
    },
    computed: {
      helpTextAuditSection() {
        return this.$store.getters.helpText("AuditSection").txt
      },
      orderedWPLines() {
        return _.sortBy(this.wplines, 'lnNo').reverse()
      }
    }
  }
</script>

<style>

  .nav-tabs .nav-link {
      color: white;
  }

  .nav-tabs .nav-item.show .nav-link, .nav-tabs .nav-link.active {
      color: #495057;
  }  

  ul.nav li a:hover {
      color: gray !important;
  }

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


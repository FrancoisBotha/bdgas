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
                  <b-dropdown-item-button  v-for="di in tasksDI" 
                                           v-on:click="actionClicked(di.menuDesc)"
                                          :key="di.id">{{ di.menuDesc }}</b-dropdown-item-button>
                </b-dropdown>
              </div>
              <div>
                <b-dropdown id="cbxct" text="Core Tests" class="ml-0 mb-2">
                  <b-dropdown-item-button  v-for="ct in tasksCT" 
                                           v-on:click="actionClicked(ct.menuDesc)"
                                          :key="ct.id">{{ ct.menuDesc }}</b-dropdown-item-button>
                </b-dropdown>
              </div>
              <div>
                <b-dropdown id="cbxpt" text="Plugin Tests" class="ml-0 mb-2">
                 <b-dropdown-item  v-for="pt in tasksPT" 
                                           v-on:click="actionClicked(pt.menuDesc)"
                                          :key="pt.id">{{ pt.menuDesc }}</b-dropdown-item>
                </b-dropdown>
              </div>              
              <div>
                <b-dropdown id="cbxdd" text="Data Display" class="ml-0 mb-2">
                 <b-dropdown-item-button  v-for="dd in tasksDD" 
                                           v-on:click="actionClicked(dd.menuDesc)"
                                          :key="dd.id">{{ dd.menuDesc }}</b-dropdown-item-button>
                </b-dropdown>
              </div>   
          </div>
        </div>
      </div>
      <div class="col-9 ml-0 mt-1" style="padding-left: 1px;">
        <b-card no-body  class="text-white bg-dark"  style="height:264px" >
          <b-tabs card >
            <b-tab title="Parameters" v-bind:class="{ active: !showHelp }" >
              <div class="bg-light text-dark" style="height: auto">
                  Tab Contents 1111
              </div>
            </b-tab>
            <b-tab title="Help" class="bg-white text-dark"  v-bind:class="{ active: showHelp }">
              <div class="card-body text-dark bg-light zeroPadding">
              <textarea class="form-control bg-light helpTextDisplay" 
                          name="txt" 
                          rows="8" 
                          readonly
                          v-model="helpTextAuditSection"></textarea> 
              </div>
            </b-tab>
          </b-tabs>
        </b-card>
      </div>
    </div>
    <div>
      <app-wpline v-for="ln in orderedWPLines" 
                  :wpline="ln"
                  :key="ln.id"
                  ></app-wpline>
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
        showHelp: true,
        selected: null,
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
      actionClicked: function(name) {
        this.showHelp = false;
      }
    },
    computed: {
      helpTextAuditSection() {
        return this.$store.getters.helpText("AuditSection").txt
      },
      tasksDI() {
        return this.$store.getters.task("DI")
      },
      tasksCT() {
        return this.$store.getters.task("CT")
      },
      tasksPT() {
        return this.$store.getters.task("PT")
      },
      tasksDD() {
        return this.$store.getters.task("DD")
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


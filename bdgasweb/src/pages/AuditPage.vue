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
                                           v-on:click="actionClicked(di)"
                                          :key="di.id">{{ di.menuDesc }}</b-dropdown-item-button>
                </b-dropdown>
              </div>
              <div>
                <b-dropdown id="cbxct" text="Core Tests" class="ml-0 mb-2">
                  <b-dropdown-item-button  v-for="ct in tasksCT" 
                                           v-on:click="actionClicked(ct)"
                                          :key="ct.id">{{ ct.menuDesc }}</b-dropdown-item-button>
                </b-dropdown>
              </div>
              <div>
                <b-dropdown id="cbxpt" text="Plugin Tests" class="ml-0 mb-2">
                 <b-dropdown-item  v-for="pt in tasksPT" 
                                           v-on:click="actionClicked(pt)"
                                          :key="pt.id">{{ pt.menuDesc }}</b-dropdown-item>
                </b-dropdown>
              </div>              
              <div>
                <b-dropdown id="cbxdd" text="Data Display" class="ml-0 mb-2">
                 <b-dropdown-item-button  v-for="dd in tasksDD" 
                                           v-on:click="actionClicked(dd)"
                                          :key="dd.id">{{ dd.menuDesc }}</b-dropdown-item-button>
                </b-dropdown>
              </div>   
          </div>
        </div>
      </div>
      <div class="col-9 ml-0 mt-1" style="padding-left: 1px;">
        <div class="card text-center text-light bg-dark" style="height:264px">
          <div class="card-header">
                <div class="float-left">            
                  <ul class="nav nav-tabs card-header-tabs ">
                    <li>
                      <router-link to="/audit/param" class="nav-item" active-class="active">
                            <a :class="[isParamActive ? 'nav-link active' : 'nav-link']">Parameters</a>
                      </router-link>
                     </li>    
                    <li>
                      <router-link to="/audit/datasource" class="nav-item" active-class="active">
                            <a :class="[isDataSourceTabActive ? 'nav-link active' : 'nav-link']">Data Sources</a>
                      </router-link>
                    </li>         
                    <li>
                      <router-link to="/audit/help" class="nav-item" active-class="active">
                            <a :class="[$route.fullPath ==='/audit/help' ? 'nav-link active' : 'nav-link']">Help</a>
                      </router-link>
                    </li>  
                  </ul>
            </div>
          </div>
          <div class="card-body text-dark bg-light">
            <router-view></router-view>
            <div class="mr-2 float-right"><a href="#" @click="onGo()" class="btn btn-success btn-sm" role="button">Go</a></div>
          </div>
        </div>
      </div>
    </div>
    <app-wpline v-for="ln in orderedWPLines" 
                  :wpline="ln"
                  :key="ln.id"
                  ></app-wpline>
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
        selectedAction: null,
      }
    },    
    methods: {
      actionClicked: function(task) {
        let route = "audit."
                    + task.taskType + "_"
                    + task.templatePath 
        this.$router.push({ name: route})
        this.selectedAction = task
        this.$store.dispatch('setActiveHelpText', task.taskHelp) 
      },
      onGo: function() {
        let wpLine = {
            id: "",
            wpId: this.wpId,
            lnNo: 0,
            taskId: this.selectedAction.id,
            taskCde: this.selectedAction.taskCde,
            taskDesc: this.selectedAction.taskDesc,
            taskParams: "",
            lnResult: [],
            lnState: "new",
        }
        this.$store.dispatch('addWpLine', wpLine)   
        this.selectedAction = null;  
        this.setDefaultHelp() 
      },
      setDefaultHelp: function() {
         this.$store.dispatch('setActiveHelpText', this.$store.getters.helpText("AuditSection").txt)
      }
    },
    computed: {
      wpId() {
            return this.$store.getters.wpId;
      },
      wpLines() {
        return this.$store.getters.wpLines
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
        return _.sortBy(this.wpLines, 'lnNo').reverse()
      },
      isParamActive: function() {
          if (this.$route.fullPath.substring(0,12) === '/audit/param') {
              return true;
          } else {
              return false;
          }
      },
      isDataSourceTabActive: function() {
          if (this.$route.fullPath.substring(0,17) === '/audit/datasource') {
              return true;
          } else {
              return false;
          }
      }
    },
    created: function () {
       this.$store.dispatch('getWpLines', this.wpId)
       this.$store.dispatch('fetchLocalDataSources')
       this.setDefaultHelp()
    },
  }
</script>

<style>
  .card-header {
      border-bottom: 0px;
  }
  .card-header .nav-link  {
      color: white!important;
  }

  .nav-link a {
      text-decoration: none!important;
  }

  .nav-tabs .nav-item.show .nav-link, .nav-tabs .nav-link.active {
      border-bottom: 1px solid white;
      color: #495057!important;
      text-decoration: none;
  }  

  ul.nav li a:hover {
      color: gray !important;
      text-decoration: none!important;
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
.card-header {
    margin-bottom: -1px;
}

</style>


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
                      <router-link :to="dynamicTo" class="nav-item" active-class="active">
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
          <div class="card-body text-dark bg-white">
            <router-view></router-view>
            <div class="mr-2 float-left ml-2" v-if="isParamActive && selectedAction != 'none'"><a href="#" @click="onCancel()" class="btn btn-outline-dark btn-sm" role="button" v-bind:class="{disabled: loadingStatus}">Cancel</a></div>
            <div class="mr-2 float-right" v-if="isParamActive && selectedAction != 'none'"><span v-if="loadingStatus">Running...  </span><a href="#" @click="onGo()" class="btn btn-success btn-sm" role="button" v-bind:class="{disabled: loadingStatus}">Go</a></div>
          </div>
        </div>
      </div>
    </div>
    <div>
      <b-alert :show="dismissCountDown"
                dismissible
                variant="warning"
                @dismissed="dismissCountDown=0"
                @dismiss-count-down="countDownChanged">
          <p>{{ alertText }}</p>
      </b-alert>
    </div>
    <app-wpline v-for="ln in orderedWPLines" 
                  :wpline="ln"
                  :key="ln.lnNo"
                  ></app-wpline>
    </div>
</template>

<script>

  import WPLine from '../components/WPLine.vue'
  import {_} from 'vue-underscore';
  import config from '../config'

  export default {
    components: {
        appWpline: WPLine
    },      
    data () {
      return {
        showHelp: true,
        selectedAction: "none",
        dismissSecs: 10,
        dismissCountDown: 0,
        showDismissibleAlert: false,
        alertText: ""
      }
    },    
    methods: {
    countDownChanged (dismissCountDown) {
        this.dismissCountDown = dismissCountDown
      },
      showAlert () {
        this.dismissCountDown = this.dismissSecs
      },     
      actionClicked: function(task) {
        let route = "audit."
                    + task.taskType + "_"
                    + task.templatePath 
        this.$router.push({ name: route})
        this.selectedAction = task
        this.$store.dispatch('setActiveHelpText', task.taskHelp) 
        this.$store.dispatch('setActiveActionTitle', task.menuDesc) 
        this.$store.dispatch('clearParameters') 
      },
      onGo: function() {
        let wpLine = {
            id: "",
            wpId: this.wpId,
            lnNo: 0,
            taskId: this.selectedAction.id,
            taskCde: this.selectedAction.taskCde,
            taskDesc: this.selectedAction.taskDesc,
            taskParams: this.$store.getters.enteredParameters,
            lnResult: "",
            lnState: "new",
        }
 console.log(this.$store.getters.enteredParameters)
        this.$store.dispatch('addWpLine', wpLine).then(response => {
          //If this was a data store action, update state
          if (wpLine.taskCde === "2001001") {
            this.$store.dispatch('setSelectedPrimaryDataSource', wpLine.taskParams[0])
            this.$store.dispatch('setSelectedPrimaryDataAlias', wpLine.taskParams[2])
          }
          this.onCancel()
        }, error => {
          // this.alertText = JSON.stringify(error, null, 4)
          // this.alertText = config.GENERAL_SERVER_ERR_MSG
          console.dir(error)
          this.alertText = error.response.data.apierror.message
          this.showAlert()
        })   
      },
      onCancel: function() {
        let route = "audit.param"
        this.$router.push({ name: route})
        this.selectedAction = "none"
        this.$store.dispatch('clearParameters') 
        this.setDefaultHelp()
      },      
      setDefaultHelp: function() {
         this.$store.dispatch('setActiveHelpText', this.$store.getters.helpText("AuditSection").txt)
      }
    },
    computed: {
      dynamicTo() {
        if (this.selectedAction == "none") {
          return "/audit/param"
        } else {
          return "/audit/param/" 
                   + this.selectedAction.taskType.toLowerCase() + "_"
                   + this.selectedAction.templatePath
        }
      },
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
      loadingStatus() {
        return this.$store.getters.loadingStatus
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


import Vue from 'vue'
import Vuex from 'vuex'
import global from './modules/global'
import datasource from './modules/datasource'
import workpaperline from './modules/workpaperline'
import auditdatasources from './modules/auditdatasources'
import localdatasource from './modules/localdatasource'
import enteredparameters from './modules/enteredparameters'
 
Vue.use(Vuex)
 
export const store = new Vuex.Store({
  modules: {
    global,
    datasource,
    workpaperline,
    auditdatasources,
    localdatasource,
    enteredparameters
  }
})
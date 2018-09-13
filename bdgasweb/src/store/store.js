import Vue from 'vue'
import Vuex from 'vuex'
import global from './modules/global'
import datasource from './modules/datasource'
import workpaperline from './modules/workpaperline'
 
Vue.use(Vuex)
 
export const store = new Vuex.Store({
  modules: {
    global,
    datasource,
    workpaperline
  }
})
import Vue from 'vue'
import underscore from 'vue-underscore';
import BootstrapVue from "bootstrap-vue"
import App from './App.vue'
import VueRouter from 'vue-router';
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap-vue/dist/bootstrap-vue.css"

import { routes } from './routes';
import { store } from './store/store'

const f = require('./filters/prettybytes');

Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(underscore);

Vue.filter("prettyBytes", f.prettyBytes)

const router = new VueRouter({
  routes
})

var vm = new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
});
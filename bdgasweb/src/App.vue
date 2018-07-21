<template>
  <div id="app">
      <div>
        <div class="container">
          <app-navbar></app-navbar>
          <app-statusbar></app-statusbar>
          <router-view></router-view>
        </div>
      </div>
  </div>
</template>
 
<script>
  import Navbar from './components/Navbar.vue'
  import Statusbar from './components/Statusbar.vue'

  export default {
    name: 'app',
    components: {
      appNavbar: Navbar,
      appStatusbar: Statusbar
    },
    data () {
      return {
        msg: "" /*javaObj.message*/
      }
    }
  }
</script>
 
<style>
#app {
 font-family: 'Avenir', Helvetica, Arial, sans-serif;
 -webkit-font-smoothing: antialiased;
 -moz-osx-font-smoothing: grayscale;
 text-align: center;
 color: #2c3e50;
 margin-top: 2px;
}
</style>

<template>
  <div id="app">
    <h1>Vue SPA</h1>
    <h1>{{ msg }}</h1>
  </div>
</template>
 
<script>
    export default {
    name: 'app',
    data () {
      return {
        msg: javaObj.message
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
 margin-top: 60px;
}
</style>

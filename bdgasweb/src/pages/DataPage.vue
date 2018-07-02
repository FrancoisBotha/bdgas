<template>
  <div>
    <app-datapagenav></app-datapagenav>
    <router-view></router-view>
  </div>
</template>

<script>
  import DataPageNav from '../components/DataPageNav.vue'

  export default {
    components: {
      appDatapagenav: DataPageNav
    }
  }
</script>
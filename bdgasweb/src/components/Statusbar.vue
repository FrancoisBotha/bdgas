<template>
    <div class="row">
        <div class="col">
            <div class="float-left mb-2 mt-1">
                <h6>Team: {{teamName}}</h6>
            </div>
        </div>
        <div class="col">
            <div class="float-none mb-2 mt-1">
                <h6>Working Paper: {{wpName}}</h6>
            </div>   
        </div>
        <div class="col">
            <div class="float-right mb-2 mt-1">
                <h6>Project: {{projectName}}</h6>
            </div> 
        </div>
    </div>
</template>

<script>
  export default {
    computed: {
        teamName() {
            return this.$store.getters.teamName;
        },
        wpName() {
            return this.$store.getters.wpName;
        },
        projectName() {
            return this.$store.getters.projectName;
        }
    }  
  }

</script>

<style scoped>
  h5 {
    color: lightslategray;
    font-family: 'Inconsolata', monospace;
  }
  h6 {
    color: lightslategray;
    font-family: 'Inconsolata', monospace;
  }
</style>
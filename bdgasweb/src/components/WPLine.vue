<template>
    <div class="row mb-3">
        <div class="col-md-11 ml-3">
            <div>
                <div class="float-left">ln[ {{ wpline.lnNo }} ]</div>
                <div class="float-right"><a href="#" @click="onDelete(wpline)" class="" role="button">x</a></div>
                <div class="clearfix"></div>
            </div>
            <div class="row taskLine">
                <div v-if="wpline.taskParams ===''" class="ml-2">[{{ wpline.taskCde }}] {{ wpline.taskDesc }}</div>
                <div v-if="wpline.taskParams !==''" class="ml-2">[{{ wpline.taskCde }}] {{ wpline.taskDesc }} {{wpline.taskParams}}</div>
            </div>
            <div class="row resultLine">
                <app-resulttable :wpline="wpline"></app-resulttable>
                <hr>
            </div>
        </div>
    </div>
</template>

<script>
  import ResultTable from '../components/ResultTable.vue'

  export default {         
    components: {
        appResulttable: ResultTable
    },      
    props: {
        wpline: Object
    },  
    computed: {
    },
    methods: {
      onDelete: function(wpline) {
        this.$store.dispatch('deleteWpLine', wpline)        
      }
    },        
  }
</script>

<style scoped>
  .taskLine {
    background-color: rgba(194, 197, 204, 0.931);
    color: black
  }
  .resultLine {
    border: 1px;
    border-style: solid;
    border-color: rgba(105, 105, 105, 0.431);
  }
</style>
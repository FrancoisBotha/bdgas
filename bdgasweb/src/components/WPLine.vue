<template>
    <div>
        <div class="row mb-3">
            <div class="col-md-11 ml-3">
                <div>
                    <div class="float-left">ln[ {{ wpline.lnNo }} ]</div>
                    <div class="float-right" v-if="wpline.lnNo === wpLineCount"><a href="#" @click="onDelete(wpline)" class="" v-b-popover.hover.top="'Delete this Working Paper Line'" role="button">x</a></div>
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
                <div class="row mt-0">
                    <div class="col">
                        <div class="float-left mb-2 mt-1">
                            <h6>User: {{wpline.userAuthId}}</h6>
                        </div>
                    </div>
                    <div class="col">
                        <div class="float-none mb-2 mt-1">
                            <h6>Date: {{wpline.startTime | moment("MMM Do YYYY H:mm")}}</h6>
                        </div>   
                    </div>
                    <div class="col">
                        <div class="float-right mb-2 mt-1">
                            <h6>Duration: {{wpline.duration }}</h6>
                        </div> 
                    </div>
                </div>
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
       wpLineCount() {
            return this.$store.getters.wpLineCount;
      },
      userName() {
        return this.$store.getters.userName
      },  
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
  h6 {
    color: lightslategray;
    font-family: 'Inconsolata', monospace;
  }
</style>

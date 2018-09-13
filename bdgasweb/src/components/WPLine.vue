<template>
    <div class="row mb-3">
        <div class="col-md-11 ml-3">
            <div>
                <div class="float-left">ln[ {{ wpline.lnNo }}]</div>
                <div class="float-right">x</div>
                <div class="clearfix"></div>
            </div>
            <div class="row taskLine">
                <div v-if="wpline.taskParams===''" class="ml-2">[{{ wpline.taskCde }}] {{ wpline.taskDesc }}</div>
                <div v-if="wpline.taskParams!==''" class="ml-2">[{{ wpline.taskCde }}] {{ wpline.taskDesc }} [{{wpline.taskParams}}]</div>
            </div>
            <div class="row resultLine">
                <div class="">ssssss</div>
                <hr>
            </div>
        </div>
    </div>
</template>

<script>
  export default {
    props: {
        wpline: Object
    },  
    computed: {
    }  
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
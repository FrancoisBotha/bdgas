<template>
    <div class="mt-3 text-left">
        <div class="row mt-2">
            <div class="col-md-7">

                <div class="row mt-3">
                    <div class="col-md-10">
                        <label>File Name</label>
                        <input type="text" class="form-control" readonly :value="dataSource.fileName"/>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-md-10">
                        <label>Type</label>
                        <input type="text" class="form-control" readonly :value="dataSource.contentType"/>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-md-10">
                        <label>Size</label>
                        <input type="text" class="form-control" readonly :value="dataSource.contentLength | prettyBytes"/>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-md-10">
                        <label>Created</label>
                        <input type="text" class="form-control" readonly :value="dataSource.createDt"/>
                    </div>
                </div>

            </div>
        </div>
        <router-link to="/data/list/table">
             <a href="#"  class="btn btn-outline-dark btn-sm mt-3" role="button">Back</a>
        </router-link>    
    </div>
</template>

<script>

  export default {  
    computed: {
      dataSource() {
        return this.$store.getters.viewDataSource
      }
    }
  }
</script>
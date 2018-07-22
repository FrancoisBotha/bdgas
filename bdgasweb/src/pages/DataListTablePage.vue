<template>
    <div class="row">
        <div class="col-md-8">

            <table class="table table-striped mt-2">
                <thead class="thead-dark">
                <tr scope="row" class="table-dark">
                    <th scope="col" class="table-dark col-md-6 text-left">File Name</th>
                    <th scope="col" class="table-dark col-md-1"></th>
                    <th scope="col" class="table-dark col-md-1"></th>
                </tr>
                </thead>
                <tbody>
                  <tr v-for="dataSource in dataSources" :key="dataSource.id">
                    <td class="text-left">{{dataSource.fileName}}</td>
                      <td><router-link to="/data/list/view">
                        <a href="#" @click="onSelectView(dataSource)"  class="btn btn-outline-dark btn-sm" role="button">View</a>
                      </router-link></td>
                     <td> <b-btn @click="onDelete(dataSource)" variant="outline-danger" size="sm">Delete</b-btn></td>          
                  </tr>  
                </tbody>
            </table>
        </div> 
        <!-- Modal Component -->
        <b-modal v-model="showConfirm"
                 id="delModal" 
                 title="Confirm Delete"
                 header-bg-variant='dark'
                 header-text-variant='light'
                 body-bg-variant='light'
                 ok-title='Delete'
                 ok-variant='danger'
                 no-close-on-backdrop='true'
                 @ok="onDeleteSelected()"
                 @cancel="onCancelSelected()"
                 >
          <div class="d-block text-left">
              <p>You are about to delete <b><i class="title">{{selectedDataSrc.fileName}}</i></b>.</p>
              <p>Do you want to proceed?</p>
          </div>
        </b-modal>
    </div>
</template>

<script>

  export default {
    data () {
      return {
         showConfirm: false,
         selectedDataSrc: {}
      }
    },
    methods: {
      onDelete: function(dataSrc) {
        this.selectedDataSrc = dataSrc;
        this.showConfirm=true;
      },
      onCancelSelected: function() {
        this.selectedDataSrc = {};
      },
      onDeleteSelected: function() {
        let dataSrc = this.selectedDataSrc;
        this.$store.dispatch('deleteDataSource', dataSrc)
      },
      onSelectView: function(dataSrc) {
        this.selectedDataSrc = dataSrc;
        this.$store.dispatch('viewDataSource', dataSrc)
      }
    },    
    computed: {
      dataSources() {
        return this.$store.getters. dataSources
      }
    },
    created: function () {
       this.$store.dispatch('fetchDataSources')
    },
  }
</script>
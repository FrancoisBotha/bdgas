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
                     <td><a href="#" class="btn btn-outline-dark btn-sm" role="button">View</a></td>
                     <td> <b-btn v-b-modal.modal1 variant="outline-danger" size="sm">Delete</b-btn></td>          
                  </tr>  
                </tbody>
            </table>
        </div> 
        <!-- Modal Component -->
        <b-modal id="modal1" title="Bootstrap-Vue">
          <p class="my-4">Hello from modal!</p>
        </b-modal>
    </div>
</template>

<script>

  export default {
    data () {
      return {
         showModal: false,
         fields: [ 'first_name', 'last_name', 'age' ],
         items: [
            { age: 40, first_name: 'Dickerson', last_name: 'Macdonald' },
            { age: 21, first_name: 'Larsen', last_name: 'Shaw' },
            { age: 89, first_name: 'Geneva', last_name: 'Wilson' }
         ]
      }
    },
    computed: {
      dataSources() {
        console.log(this.$store.getters.dataSources)
        return this.$store.getters. dataSources
      }
    },
    created: function () {
       this.$store.dispatch('fetchDataSources')
    },
  }
</script>
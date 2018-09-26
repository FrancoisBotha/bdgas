// Dynamic Vue Table, 
// Adapted from blog:
// http://www.developerdrive.com/2017/07/creating-a-data-table-in-vue-js/

<template>
    <div>
        <table id="secondTable" class="mb-2 ml-2 mt-2">
        <thead>
            <tr>
            <th v-for="col in columns" :key="col" >{{col}}</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="row in rows" :key="row">
            <td v-for="col in columns" :key="col">{{row[col]}}</td>
            </tr>
        </tbody>
        </table>     
    </div>
</template>

<script>

  export default {
    data () {
      return {
       }
    }, 
    props: {
        wpline: Object
    },  
    computed: {
        "columns": function columns() {
            if (this.rows.length == 0) {
                return [];
            }
            return Object.keys(this.rows[0])
        },
        rows() {
            let resultData = JSON.parse(this.wpline.lnResult)
            return resultData
        }
    }
  }
</script>

<style scoped>
    table, td, th {
        border: 3px solid black;
    }

    table {
        border-collapse: collapse;
        width: 100%;
    }

    th {
        text-align: left;
    }
</style>


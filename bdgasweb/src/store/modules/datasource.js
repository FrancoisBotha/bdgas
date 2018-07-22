import axios from 'axios'
import config from '../../config'

var axiosProxyS = axios.create({
    baseURL: config.DATASOURCE_ENDPOINT,
    timeout: 3000,
  })

  var deleteEndpoint = config.DATASOURCE_ENDPOINT; 

const state = {
    dataSources: [],
    viewDataSource: {}
}
 
const getters = {
    dataSources: state => {
        return state.dataSources;
    },
    viewDataSource: state => {
        return state.viewDataSource;
    }            
}
 
const mutations = {
    'SET_DATASOURCES' (state, retrievedDataSources) {
        state.dataSources = retrievedDataSources;
    },
    'GET_DATASOURCE' (state, dataSource) {
        state.selectedDataSource = dataSource;
    },
    'DELETE_DATASOURCE' (state, dataSource) {
        var dataSources = state.dataSources;
        dataSources.splice(dataSources.indexOf(dataSource),1)
    }, 
    'VIEW_DATASOURCE' (state, selectedDataSource) {
        state.viewDataSource = selectedDataSource;
    },

}
 
const actions = {
    fetchDataSources: ({commit}) => {
        axiosProxyS.get()
        .then(function (res) {
          commit('SET_DATASOURCES', res.data)
        })
        .catch(function (err) {
          console.log(err)
        })        
    },
    getDataSource: ({commit}, dataSource) => {
        axiosProxyS.get()
        .then(function (res) {
          commit('GET_DATASOURCE', res.data)
        })
        .catch(function (err) {
          console.log(err)
        })        
    },
    deleteDataSource: ({commit}, dataSource) => {
        var url = deleteEndpoint + "/" + dataSource.id;
        let config = {
        };
        axios.delete(url, config)
        .then(function(res) {
            commit('DELETE_DATASOURCE', dataSource)
        })
        .catch(function (err) {
            console.log(err);
        })       
    },
    viewDataSource: ({commit}, dataSource) => {
        commit('VIEW_DATASOURCE', dataSource)     
    }    
}
 
export default {
  state,
  getters,
  mutations,
  actions
}
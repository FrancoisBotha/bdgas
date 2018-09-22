import axios from 'axios'
import config from '../../config'

var axiosProxyS = axios.create({
    baseURL: config.LOCALDATASOURCE_ENDPOINT,
    timeout: 3000,
  })

  var deleteEndpoint = config.LOCALDATASOURCE_ENDPOINT; 

const state = {
    localDataSources: [],
    viewLocalDataSource: {}
}
 
const getters = {
    localDataSources: state => {
        return state.localDataSources;
    },
    viewLocalDataSource: state => {
        return state.viewLocalDataSource;
    }            
}
 
const mutations = {
    'SET_LOCALDATASOURCES' (state, retrievedDataSources) {
        state.localDataSources = retrievedDataSources;
    },
}
 
const actions = {
    fetchLocalDataSources: ({commit}) => {
        axiosProxyS.get()
        .then(function (res) {
          commit('SET_LOCALDATASOURCES', res.data)
        })
        .catch(function (err) {
          console.log(err)
        })        
    },   
}
 
export default {
  state,
  getters,
  mutations,
  actions
}
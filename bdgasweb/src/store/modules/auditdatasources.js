import axios from 'axios'
import config from '../../config'

var axiosProxyS = axios.create({
    baseURL: config.LOCALDATASOURCE_ENDPOINT,
    timeout: 3000,
  })

var deleteEndpoint = config.LOCALDATASOURCE_ENDPOINT; 

const state = {
    auditDataSources: [],
}
 
const getters = {
    auditDataSources: state => {
        return state.auditDataSources;
    },
    viewLocalDataSource: state => {
        return state.viewLocalDataSource;
    }            
}
 
const mutations = {
    'SET_AUDITDATASOURCES' (state, retrievedDataSources) {
        state.auditDataSources = retrievedDataSources;
    },
}
 
const actions = { 
    setAuditDataSources: ({commit}, dataSource) => {
        commit('SET_AUDITDATASOURCES', dataSource)     
    }      
}
 
export default {
  state,
  getters,
  mutations,
  actions
}
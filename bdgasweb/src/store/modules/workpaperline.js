import axios from 'axios'
import config from '../../config'

var axiosProxyS = axios.create({
    baseURL: config.WPLINE_ENDPOINT,
    timeout: 3000,
  })

var deleteEndpoint = config.DATASOURCE_ENDPOINT; 

const state = {
    wplines: [],
}

const getters = {
    wplines: state => {
        return state.wplines;
    },         
}

const mutations = {
    'SET_WPLINES' (state, retrievedWpLines) {
        state.wplines = retrievedWpLines;
    },
    'DELETE_WPLINE' (state, wpLine) {
        var wplines = state.wplines;
        wplines.splice(wplines.indexOf(wpLine),1)
    },
    'ADD_WPLINE' (state, wpLine) {
        var wplines = state.wplines;
        wplines.push(wpLine)
    }
}
 
const actions = {
    fetchWpLines: ({commit}) => {
        axiosProxyS.get()
        .then(function (res) {
          commit('SET_WPLINES', res.data)
        })
        .catch(function (err) {
          console.log(err)
        })        
    },
    deleteWpLine: ({commit}, wpLine) => {
        var url = deleteEndpoint + "/" + wpLine.id;
        let config = {
        };
        axios.delete(url, config)
        .then(function(res) {
            commit('DELETE_WPLINE', dataSource)
        })
        .catch(function (err) {
            console.log(err);
        })       
    },
    addWpLine: ({commit}, wpLine) => {
        axiosProxyS.post(wpLine)
        .then(function (res) {
          commit('ADD_WPLINE', wpLine)
        })
        .catch(function (err) {
          console.log(err)
        })       
    }
}
 
export default {
  state,
  getters,
  mutations,
  actions
}
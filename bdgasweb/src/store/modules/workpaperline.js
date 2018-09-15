import axios from 'axios'
import config from '../../config'

var baseURL = config.WPLINE_ENDPOINT; 

var axiosProxyS = axios.create({
    baseURL: baseURL,
    timeout: 3000,
  })

const state = {
    wpLines: [],
}

const getters = {
    wpLines: state => {
        return state.wpLines;
    },         
}

const mutations = {
    'SET_WPLINES' (state, retrievedWpLines) {
        state.wpLines = retrievedWpLines;
    },
    'DELETE_WPLINE' (state, wpLine) {
        var wplines = state.wpLines;
        wplines.splice(wplines.indexOf(wpLine),1)
    },
    'ADD_WPLINE' (state, wpLine) {
        var wplines = state.wpLines;
        wplines.push(wpLine)
    }
}
 
const actions = {
    getWpLines: ({commit}, id) => {
        axios({
            method: 'get',
            url: baseURL + "/workingpaper/" + id,
            config: { headers: {'Content-Type': 'application/json' }}
            })
            .then(function (res) {
                commit('SET_WPLINES', res.data)
              })
              .catch(function (err) {
                console.log(err)
            }
        );              
    },
    deleteWpLine: ({commit}, wpLine) => {
        var url = baseURL + "/" + wpLine.id;
        let config = {
        };
        axios.delete(url, config)
        .then(function(res) {
            commit('DELETE_WPLINE', wpLine)
        })
        .catch(function (err) {
            console.log(err);
        })       
    },
    addWpLine: ({commit}, wpLine) => {
        let data = new FormData();
        axios({
            method: 'post',
            url: baseURL,
            data: wpLine,
            config: { headers: {'Content-Type': 'application/json' }}
            })
            .then(function (response) {
                commit('ADD_WPLINE', wpLine)
            })
            .catch(function (err) {
                console.log(err)
        });      
    }
}
 
export default {
  state,
  getters,
  mutations,
  actions
}
import axios from 'axios'
import config from '../../config'

const state = {
    enteredParameters: []
}
 
const getters = {
    enteredParameters: state => {
        return state.enteredParameters;
    },         
}
 
const mutations = {
    'ADD_PARAMETER' (state, parameter) {
        state.enteredParameters.push(parameter)
    },
    'SET_PARAMETER' (state, payload) {
        state.enteredParameters[payload.i] = payload.parameter
    },
    'CLEAR_PARAMETERS' (state) {
        state.enteredParameters = []
    },
}
 
const actions = {
    addParameter: ({commit}, parameter) => {
        commit('ADD_PARAMETER', parameter)     
    },  
    setParameter: ({commit}, payload) => {
        commit('SET_PARAMETER', payload)     
    }, 
    clearParameters: ({commit}) => {
        commit('CLEAR_PARAMETERS')       
    },
}
 
export default {
  state,
  getters,
  mutations,
  actions
}
import axios from 'axios'

var axiosProxyS = axios.create({
  baseURL: 'http://localhost:8001/',
  timeout: 2000,
})

const state = {
    signedS3Url: "aagff"
}
 
const getters = {
    signedS3Url: state => {
        return state.signedS3Url;
    }
}
 
const mutations = {
    'SET_SIGNEDS3URL' (state, signedUrl) {
        state.signedS3Url = signedUrl;
    }
}
 
const actions = {
    fetchSignedS3Url: ({commit}) => {
        axiosProxyS.get('/data')
        .then(function (res) {
            console.log(res.data)
          commit('SET_SIGNEDS3URL', res.data.url)
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
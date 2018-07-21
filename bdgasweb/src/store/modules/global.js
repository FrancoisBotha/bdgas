import axios from 'axios'

var axiosProxyS = axios.create({
  baseURL: 'http://localhost:8001/',
  timeout: 3000,
})

const state = {
    signedS3Url: "",
    teamName: teamObjJava.name,
    projectName: projectObjJava.name,
    wpName: wpObjJava.name,
    teamId: teamObjJava.id,
    projectId: projectObjJava.id,
    wpId: wpObjJava.id
}
 
const getters = {
    signedS3Url: state => {
        return state.signedS3Url;
    },
    teamName: state => {
        return state.teamName;
    },
    projectName: state => {
        return state.projectName;
    },
    wpName: state => {
        return state.wpName;
    },
    teamId: state => {
        return state.teamId;
    },
    projectId: state => {
        return state.projectId;
    },
    wpId: state => {
        return state.wpId;
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
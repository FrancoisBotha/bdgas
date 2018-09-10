import axios from 'axios'

var axiosProxyS = axios.create({
  baseURL: 'http://localhost:8001/',
  timeout: 3000,
})

let teamObjTemp = {
    name: "TEAM",
    id: "TEAMID"
}

let projectObjTemp = {
    name: "PROJECT",
    id: "PROJECTID"
}

let wpObjTemp = {
    name: "WP",
    id: "WPID"
}

let tasksObjTemp = []
let helpTextsObjTemp = []

var teamObj = (typeof teamObjJava != "undefined") ? teamObjJava : teamObjTemp;
var projectObj = (typeof projectObjJava != "undefined")  ? projectObjJava : projectObjTemp;
var wpObj = (typeof wpObjJava!= "undefined") ? wpObjJava : wpObjTemp;
var tasksObj = (typeof tasksObjJava!= "undefined") ? tasksObjJava : tasksObjTemp;
var helpTextsObj = (typeof helpTextsObjJava!= "undefined") ? helpTextsObjJava : helpTextsObjTemp;

const state = {
    signedS3Url: "",
    teamName: teamObj.name,
    projectName: projectObj.name,
    wpName: wpObj.name,
    teamId: teamObj.id,
    projectId: projectObj.id,
    wpId: wpObj.id,
    tasks: tasksObj,
    helpTexts: helpTextsObj
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
    },
    tasks: state => {
        return state.tasks;
    },
    task: state => {
        return  type => state.tasks.filter(obj => {
            return obj.taskType === type
        });
    },
    helpTexts: state => {
        return state.helpTexts;
    },        
    helpText: state => {
        return name => state.helpTexts.find(obj => {
            return obj.name === name
          });
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
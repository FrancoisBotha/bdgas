import axios from 'axios'
import config from '../../config'

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
let delimitersObjTemp = []

var teamObj = (typeof teamObjJava != "undefined") ? teamObjJava : teamObjTemp;
var projectObj = (typeof projectObjJava != "undefined")  ? projectObjJava : projectObjTemp;
var wpObj = (typeof wpObjJava != "undefined") ? wpObjJava : wpObjTemp;
var tasksObj = (typeof tasksObjJava != "undefined") ? tasksObjJava : tasksObjTemp;
var delimitersObj = (typeof delimitersObjJava != "undefined") ? delimitersObjJava : delimitersObjTemp;
var helpTextsObj = (typeof helpTextsObjJava != "undefined") ? helpTextsObjJava : helpTextsObjTemp;

const state = {
    signedS3Url: "",
    teamName: teamObj.name,
    projectName: projectObj.name,
    wpName: wpObj.name,
    teamId: teamObj.id,
    projectId: projectObj.id,
    wpId: wpObj.id,
    tasks: tasksObj,
    helpTexts: helpTextsObj,
    delimiters: delimitersObj,
    activeHelpText: 
    "",
    localMode: config.LOCAL_MODE, //Wether or local (devl/testing) data is used
    selectedPrimaryDataSource: "<Please Select Data Source>",
    selectedPrimaryDataAlias: "<None selected>",
    primarySchema: "",
    primaryNumericSchema: "",
    activeActionTitle: ""
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
    delimiters: state => {
        return state.delimiters;
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
    }, 
    activeHelpText: state => {
        return state.activeHelpText;
    },       
    localMode: state => {
        return state.localMode;
    }, 
    selectedPrimaryDataSource: state => {
        return state.selectedPrimaryDataSource;
    }, 
    selectedPrimaryDataAlias: state => {
        return state.selectedPrimaryDataAlias;
    },    
    activeActionTitle: state => {
        return state.activeActionTitle;
    }, 
    primarySchema: state => {
        return state.primarySchema;
    }, 
    primaryNumericSchema: state => {
        return state.primaryNumericSchema;
    }, 
}
 
const mutations = {
    'SET_SIGNEDS3URL' (state, signedUrl) {
        state.signedS3Url = signedUrl;
    },   
    'SET_SELECTEDPRIMARYDATASOURCE' (state, dataSource) {
        state.selectedPrimaryDataSource = dataSource;
    },
    'SET_SELECTEDPRIMARYDATAALIAS' (state, Alias) {
        state.selectedPrimaryDataAlias = Alias;
    },
    'SET_PRIMARYSCHEMA' (state, schema) {
        state.primarySchema = schema;
    },
    'SET_PRIMARYNUMERICSCHEMA' (state, schema) {
        state.primaryNumericSchema = schema;
    },
    'SET_ACTIVEHELPTEXT' (state, activeHelpText) {
        state.activeHelpText = activeHelpText;
    },
    'SET_ACTIVEACTIONTITLE' (state, title) {
        state.activeActionTitle = title;
    },
   
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
    },
    setActiveHelpText: ({commit}, helpText) => {
        commit('SET_ACTIVEHELPTEXT', helpText)     
    },
    setSelectedPrimaryDataSource: ({commit}, dataSource) => {
        commit('SET_SELECTEDPRIMARYDATASOURCE', dataSource)     
    },
    setSelectedPrimaryDataAlias: ({commit}, alias) => {
        commit('SET_SELECTEDPRIMARYDATAALIAS', alias)     
    },
    setPrimarySchema: ({commit}, schema) => {
        commit('SET_PRIMARYSCHEMA', schema)     
    },
    setPrimaryNumericSchema: ({commit}, schema) => {
        commit('SET_PRIMARYNUMERICSCHEMA', schema)     
    },
    setActiveActionTitle: ({commit}, title) => {
        commit('SET_ACTIVEACTIONTITLE', title)     
    },
    
}
 
export default {
  state,
  getters,
  mutations,
  actions
}
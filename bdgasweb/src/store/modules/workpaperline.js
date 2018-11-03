/*****************************************************************************
 * Copyright 2018 Francois Botha                                             *
 *                                                                           *
 * Licensed under the Apache License, Version 2.0 (the "License");           *
 * you may not use this file except in compliance with the License.          *
 * You may obtain a copy of the License at                                   *
 *                                                                           *
 *  http://www.apache.org/licenses/LICENSE-2.0                               *
 *                                                                           *
 * Unless required by applicable law or agreed to in writing, software       *
 * distributed under the License is distributed on an "AS IS" BASIS,         *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 * See the License for the specific language governing permissions and       *
 * limitations under the License.                                            *
 *                                                                           *
 *****************************************************************************/
import axios from 'axios'
import config from '../../config'

var baseURL = config.WPLINE_ENDPOINT; 

var axiosProxyS = axios.create({
    baseURL: baseURL,
    timeout: 3000,
  })

const state = {
    wpLines: [],
    wpLineCount: 0,
    loadingStatus: false
}

const getters = {
    wpLines: state => {
        return state.wpLines;
    },    
    wpLineCount: state => {
        return state.wpLines.length;
    },  
    loadingStatus: state => {
        return state.loadingStatus;
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
    },
    'SET_LOADINGSTATUS' (state, loadingStatus) {
        state.loadingStatus = loadingStatus;
    }, 
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
    addWpLine: ({commit, dispatch}, wpLine) => {
        return new Promise((resolve, reject) => {
            let data = new FormData();
            commit('SET_LOADINGSTATUS', true)
            axios({
                method: 'post',
                url: baseURL,
                timeout: 36000000,
                data: wpLine,
                config: { headers: {'Content-Type': 'application/json' }}
                })
                .then(function (response) {
                    wpLine.id = response.data.id
                    wpLine.lnNo = response.data.lnNo
                    wpLine.taskCde = response.data.taskCde
                    wpLine.taskDesc = response.data.taskDesc
                    wpLine.taskParams = response.data.taskParams
                    wpLine.lnResult = response.data.lnResult
                    wpLine.lnState = response.data.lnState
                    wpLine.userAuthId = response.data.userAuthId
                    wpLine.duration = response.data.duration
                    wpLine.startTime = response.data.startTime
                    commit('ADD_WPLINE', wpLine)
                    commit('SET_LOADINGSTATUS', false)

                    //If this was a data store action, update state
                    if (wpLine.taskCde === "2001001") {
                        dispatch('setSelectedPrimaryDataSource', wpLine.taskParams[0])
                        dispatch('setSelectedPrimaryDataAlias', wpLine.taskParams[2])
                        let resultData = JSON.parse(wpLine.lnResult)

                        //ToDo: Also include float data types...
                        let numericFlds = resultData.filter(fld => fld.data_type == "int")
                        dispatch('setPrimarySchema', resultData)
                        dispatch('setPrimaryNumericSchema', numericFlds)
                    }

                    resolve(response)
                })
                .catch(function (err) {
                    commit('SET_LOADINGSTATUS', false)
                    reject(err)
            });
        })      
    },
    setLoadingStatus: ({commit}, loadingStatus) => {
        commit('SET_LOADINGSTATUS', loadingStatus)     
    } 
}
 
export default {
  state,
  getters,
  mutations,
  actions
}
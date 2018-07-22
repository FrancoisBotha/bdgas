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
import config from '../config'
import { store } from '../store/store'


export default {
  addDataSource (file) {      
    let endpoint = config.DATASOURCE_ENDPOINT;

    var bodyFormData = new FormData();
    bodyFormData.set('teamId', store.getters.teamId);
    bodyFormData.set('fileName', file.name);
    bodyFormData.set('objectKey', file.name);

    return axios({
        method: 'post',
        url: endpoint,
        data: bodyFormData
      }).then((res) => {
          console.log("Loggin data source response:")
          console.log(res.data);
        return Promise.resolve(res.data || '/')
      })
      .catch((err) => {
        console.error(err)
        return Promise.reject('/')
      })
  }
}
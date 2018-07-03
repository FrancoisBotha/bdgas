import axios from 'axios'
import config from '../config'

export default {
  getSignedURL (file) {      
    let endpoint = config.AWS_GETSIGNEDURL_ENDPOINT

    var bodyFormData = new FormData();
    bodyFormData.set('fileName', file.name);
    bodyFormData.set('contentType', file.type);

    return axios({
        method: 'post',
        url: endpoint,
        data: bodyFormData
      }).then((res) => {
          console.log('a')
          console.log(res.data.url);
        return Promise.resolve(res.data.url || '/')
      })
      .catch((err) => {
        console.error(err)
        return Promise.reject('/')
      })
  }
}
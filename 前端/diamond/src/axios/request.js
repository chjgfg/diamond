import axios from 'axios'

axios.defaults.baseURL = "http://127.0.0.1:5555/";

export default function request(url, data = {}, type = 'get') {
  return new Promise((resolve, reject) => {
    axios({url: url, data: data, method: type}).then(res => {
      resolve(res.data);
    }).catch(err => {
      reject(err)
    });
  })
}

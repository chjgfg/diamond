import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'

Vue.config.productionTip = false;

import {get, set} from "./utils/local"

// window.addEventListener("beforeunload",()=>{
// //   localStorage.setItem("state",JSON.stringify(this.$store.state))
// // });
//
// set(false, "", "");

router.beforeEach((to, from, next) => {//到哪去,从哪来,然后干嘛
    if (to.meta.requireAuth) {//判断哪个要拦截
      if (get("user").flag === true) {//判断存的变量
        next()//如果是就继续跳转
      } else {//如果不是那就跳转到登录页面
        next({
          path: '*',
          /*query: {redirect: to.fullPath}*/
        })
      }
    } else {//如果不拦截直接跳转
      if (get("user").flag === true) {
        next('/database');
      } else {
        next();
      }
    }
  }
);





new Vue({
  router,
  store,
  vuetify,
  render: function (h) {
    return h(App)
  }
}).$mount('#app');

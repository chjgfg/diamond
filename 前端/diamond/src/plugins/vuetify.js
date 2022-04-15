import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';
import 'material-design-icons-iconfont/dist/material-design-icons.css';//引入图片文字
import 'font-awesome/css/font-awesome.min.css' // 确保您正在使用 css-loader

Vue.use(Vuetify);

export default new Vuetify({
  icons: {
    iconfont: "md" || "fa",
  }
});

module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    host: "127.0.0.1", //要设置当前访问的ip 否则失效
    port: 8182,//当前web服务端口
    // open: true, //浏览器自动打开页面
    // proxy: {
    //   '/api': {
    //     target: 'https://geo.ipify.org/api',//目标地址
    //     //ws: true,//是否代理websocket
    //     changeOrigin: true,//是否跨域
    //     pathRewrite: {
    //       '^/api': ''//url重写
    //     }
    //   }
    // }
  },
  pluginOptions: {
    electronBuilder: {
      // 渲染进程也可以获取原生node包
      nodeIntegration: true,
      // 打包配置
      builderOptions: {
        nsis: {
          // 一键安装，如果设为true，nsis设置就无意义请直接删除 nsis 配置
          oneClick: false,
          // true全用户安装，false安装到当前用户
          perMachine: true,
          // 允许修改安装目录
          allowToChangeInstallationDirectory: true,
          // 创建桌面图标
          createDesktopShortcut: true,
          // 创建开始菜单图标
          createStartMenuShortcut: true,
          // 快捷方式的名称,默认为应用程序名称
          shortcutName: '第一个桌面应用',
        }
      }
    }
  }
};

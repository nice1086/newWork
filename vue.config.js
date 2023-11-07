const { defineConfig } = require('@vue/cli-service')
const path = require('path')
const resolve = (dir) => {
  return path.join(__dirname,dir)
}

module.exports = defineConfig({
  transpileDependencies: true
}
)

module.exports = {
  devServer: {
    host:'localhost',
    port: 8181,
    open: true,
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // 后端服务地址
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  },
  publicPath:"/",
  configureWebpack(){
    return {
      resolve:{
        alias:{
          '@':resolve('src')
        }
      }
    }
  }
}
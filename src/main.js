import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';
import router from './router/index.js'
import store from './store/store.js';


const app=createApp(App);
app.use(router);
app.use(store);
app.use(ElementPlus);
app.mount('#app');

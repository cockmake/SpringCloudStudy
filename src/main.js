import { createApp } from 'vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import './style.css';
import App from './App.vue';
import axios from "axios";
import router from "./routers.js";
import zhLocale from 'element-plus/lib/locale/lang/zh-cn'

axios.defaults.baseURL = "http://127.0.0.1:8080"

const app = createApp(App)
app.use(ElementPlus, {
    locale: zhLocale
})
app.use(router)
app.mount('#app')
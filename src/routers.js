import { createRouter, createWebHistory } from 'vue-router'
import {ElMessage} from "element-plus";

//注册路由
const routes = [
    {
        path: '/',
        name: 'init',
        redirect: 'home',
        children: [
            {
                path: '/home',
                name: 'home',
                component: () => import('./views/Home.vue')
            }
        ],
    },
    {
        path: '/order',
        name: 'order',
        component: () => import('./views/OrderManage.vue')
    }
];
const router = createRouter({
    routes,
    history: createWebHistory()
});
router.beforeEach((to, from, next) => {
    if (to.path !== "/home"){
        let username = localStorage.getItem('username');
        if (username !== null) { // 查询本地存储信息是否已经登陆
            next();
        } else {
            next({
                path: '/home', // 未登录则跳转至login页面
            });
            ElMessage({
                type: 'warning',
                message: '未登录！',
                duration: 3000
            })
        }
    }else next()

})
export default router;
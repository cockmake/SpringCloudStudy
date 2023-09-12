import { createRouter, createWebHistory } from 'vue-router'

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

export default router;
import {createRouter, createWebHashHistory} from 'vue-router';
import $utils from '@/commons/utils';

/**
 * 路由配置
 * @type {Router}
 */
let $route = createRouter({
    history: createWebHashHistory(),
    routes: [{
        path: '/',
        name: 'ContainerPage',
        redirect: '/home',
        component: () => import('../pages/ContainerPage.vue'),
        meta: {
            needLogin: true
        },
        children: [{
            path: 'home',
            name: 'HomePage',
            component: () => import('../pages/HomePage.vue')
        }, {
            path: 'user/info',
            name: 'UserInfo',
            component: () => import('../pages/UserInfo.vue')
        }, {
            path:'legal/info/manage',
            name:'LegalInfoManage',
            component: () => import('../pages/LegalInfoManage.vue')
        },{
            path:'lawyer/member/manage',
            name:'LawyerMemberManage',
            component: () => import('../pages/LawyerMemberManage.vue')
        },{
            path:'user/manage',
            name:'UserManage',
            component: () => import('../pages/UserManage.vue')
        },{
            path:'legal/advice/order',
            name:'LegalAdviceOrder',
            component: () => import('../pages/LegalAdviceOrder.vue')
        },{
            path:'order/chat',
            name:'OrderChat',
            component: () => import('../pages/OrderChat.vue')
        },{
            path: 'comment',
            name: 'Comment',
            component: ()=>import('../pages/CommentPage.vue')
        }]
    }, {
        path: '/login',
        name: 'LoginPage',
        component: () => import('../pages/LoginPage.vue')
    }, {
        path: '/register',
        name: 'RegisterPage',
        component: () => import('../pages/RegisterPage.vue'),
    }, {
        path: '/register/success',
        name: 'RegisterSuccess',
        component: () => import('../pages/RegisterSuccess.vue')
    }, {
        path: '/password/change',
        name: 'PasswordForget',
        component: () => import('../pages/PasswordChange.vue')
    }]
})
/**
 * 导航守卫 - 前置
 */
$route.beforeEach((to, from, next) => {
    $utils.hideLoading();
    if (to.meta.needLogin && !$utils.getAuthorization()) next('/login');
    else next();
});

export default $route;
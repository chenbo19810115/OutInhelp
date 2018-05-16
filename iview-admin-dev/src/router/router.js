import Main from '@/views/Main.vue';

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
    path: '/login',
    name: 'login',
    meta: {
        title: 'Login - 登录'
    },
    component: () => import('@/views/login.vue')
};

export const regRouter = {
    path: '/reg',
    name: 'reg',
    meta: {
        title: 'reg-注册'
    },
    component: () => import('@/views/reg.vue')
}

export const page404 = {
    path: '/*',
    name: 'error-404',
    meta: {
        title: '404-页面不存在'
    },
    component: () => import('@/views/error-page/404.vue')
};

export const page403 = {
    path: '/403',
    meta: {
        title: '403-权限不足'
    },
    name: 'error-403',
    component: () => import('@//views/error-page/403.vue')
};

export const page500 = {
    path: '/500',
    meta: {
        title: '500-服务端错误'
    },
    name: 'error-500',
    component: () => import('@/views/error-page/500.vue')
};

export const locking = {
    path: '/locking',
    name: 'locking',
    component: () => import('@/views/main-components/lockscreen/components/locking-page.vue')
};

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    redirect: '/home',
    component: Main,
    children: [
       { path: 'home', title: {i18n: 'home'}, name: 'home_index', component: () => import('@/views/home/home.vue') },
       { path: 'message', title: '消息中心', name: 'message_index', component: () => import('@/views/message/message.vue') },
       { path: 'databackrestore', title: '数据备份还原', name: 'databackrestore_index', component: () => import('@/views/databackrestore/databackrestore.vue') }
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [
    // {
    //     path: '/ownspace',
    //     icon: 'ios-grid-view',
    //     name: 'ownspace',
    //     access: 1,
    //     title: '个人收益',
    //     component: Main,
    //     children: [
    //         { path: 'index', title: '个人收益', name: 'ownspace_index', access: 1, component: () => import('@/views/own-space/own-space.vue') }
    //     ]
    // },
    {
        path: '/bangdian',
        icon: 'ios-grid-view',
        name: 'bangdian',
        access: 1,
        title: '收益总榜单',
        component: Main,
        children:[
            {path: 'index', title: '收益总榜单', name: 'shouyi_bangdian_index', access: 1, component: () => import('@/views/sumbangdan/sumbangdan.vue') }
        ]
    },

    {
        path: '/usermgn',
        icon: 'ios-grid-view',
        name: 'usermgn',
        access: 0,
        title: '用户管理',
        component: Main,
        children:[
            {path: 'index', title: '用户管理', name: 'usermgn_index', access: 0, component: () => import('@/views/usermgn/usermgn.vue') }
        ]
    },

    // {
    //     path: 'touzimgn',
    //     icon: 'ios-grid-view',
    //     name: 'touzimgn',
    //     access: 0,
    //     title: '投资管理',
    //     component: Main,
    //     children:[
    //         {path: 'index', title: '投资管理', name: '/touzimgn_index', access: 0, component: () => import('@/views/touzimgn/touzimgn.vue') }
    //     ]
    // },

    {
        path: '/touzishouyimgn',
        icon: 'ios-grid-view',
        name: 'touzishouyimgn',
        access: 0,
        title: '投资收益管理',
        component: Main,
        children:[
            {path: 'index', title: '投资收益管理', name: 'touzishouyimgn_index', access: 0, component: () => import('@/views/touzishouyimgn/touzishouyimgn.vue') }
        ]
    },

    {
        path: '/touzishouyixiangdan',
        icon: 'ios-grid-view',
        name: 'touzishouyixiangdan',
        access: 0,
        title: '投资收益详细信息',
        component: Main,
        children:[
            {path: 'index', title: '投资收益详细信息', name: 'touzishouyixiangdan_index', access: 0, component: () => import('@/views/touzishouyixiangdan/touzishouyixiangdan.vue') }
        ]
    },
    {
        path: '/tixianmgn',
        icon: 'ios-grid-view',
        name: 'tixianmgn',
        access: 0,
        title: '用户提现处理',
        component: Main,
        children:[
            {path: 'index', title: '用户提现处理', name: 'tixianmgn_index', access: 0, component: () => import('@/views/tixianmgn/tixianmgn.vue') }
        ]
    },
    {
        path: '/mgnoptrec',
        icon: 'ios-grid-view',
        name: 'mgnoptrec',
        access: 0,
        title: '管理员操作日志',
        component: Main,
        children:[
            {path: 'index', title: '管理员操作日志', name: 'mgnoptrec_index', access: 0, component: () => import('@/views/mgnoptrec/mgnoptrec.vue') }
        ]
    },

    {
        path: '/userreg',
        icon: 'ios-grid-view',
        name: 'userreg',
        access: 0,
        title: '注册请求管理',
        component: Main,
        children:[
            {path: 'index', title: '注册请求', name: 'userreg_index', access: 0, component: () => import('@/views/userreg/userreg.vue') }
        ]
    }
];

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
    loginRouter,
    regRouter,
    otherRouter,
    locking,
    ...appRouter,
    page500,
    page403,
    page404
];

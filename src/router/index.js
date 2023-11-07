import { createRouter, createWebHistory } from 'vue-router';

export const routes = [
  {
    path: '/',
    redirect:"/home"
  },
  {
    path: '/home',
    component: () => import("@/components/HomeComponent.vue"),
    meta:{
        title:'首页'
    }
  },
  {
    path: '/login',
    component: () => import("@/components/LoginComponent.vue"),
    meta:{
      title:'登陆页面'
  }
  },
  {
    path: '/video',
    component: () => import("@/components/VideoComponent.vue"),
    meta:{
      title:'视频页面'
  }
  }
]

const router = createRouter({
    history: createWebHistory(),
    routes
  })
  
export default router

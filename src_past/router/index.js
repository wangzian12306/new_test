import { createRouter, createWebHistory } from 'vue-router'
import AdminLayout from '../components/AdminLayout.vue'

const routes = [
  {
    path: '/',
    redirect: '/admin/announcements'
  },
  {
    path: '/admin',
    component: AdminLayout,
    redirect: '/admin/announcements',
    children: [
      {
        path: 'announcements',
        name: 'Announcements',
        component: () => import('../views/admin/Announcements.vue')
      },
      {
        path: 'posts',
        name: 'Posts',
        component: () => import('../views/admin/Posts.vue')
      },
      {
        path: 'pet-encyclopedia',
        name: 'PetEncyclopedia',
        component: () => import('../views/admin/PetEncyclopedia.vue')
      },
      {
        path: 'users',
        name: 'Users',
        component: () => import('../views/admin/Users.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 添加导航守卫用于调试
router.beforeEach((to, from, next) => {
  console.log('Navigating to:', to.path)
  next()
})

export default router
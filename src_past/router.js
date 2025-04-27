import { createRouter, createWebHistory } from 'vue-router'
import Home from './views/Home.vue'
import Foster from './views/Foster.vue'
import PetCircle from './views/PetCircle.vue'
import PetEncyclopedia from './views/PetEncyclopedia.vue'

// 使用 createRouter 和 createWebHistory
const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/foster',
      name: 'foster',
      component: Foster
    },
    {
      path: '/pet-circle',
      name: 'pet-circle',
      component: PetCircle
    },
    {
      path: '/pet-encyclopedia',
      name: 'pet-encyclopedia',
      component: PetEncyclopedia
    }
  ]
})

export default router
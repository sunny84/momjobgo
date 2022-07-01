import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import RecipeWriteView from '@/views/RecipeWriteView.vue'
import LoginView from '@/views/LoginView.vue'
import LoginViewkko from '@/views/LoginViewkko.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path : '/write',
    name: 'write',
    component: RecipeWriteView
  },
  {
    path : '/login',
    name: 'login',
    component: LoginView
  },
  {
    path : '/loginkko',
    name: 'loginkko',
    component: LoginViewkko
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

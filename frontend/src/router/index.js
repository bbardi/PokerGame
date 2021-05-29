import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Topup from '../views/Topup.vue'
import Game from '../views/Lobbies.vue'
import UserAdmin from '../views/UserAdmin.vue'
import MainGame from '../views/MainGame.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/topup',
    name: 'Topup',
    component: Topup
  },
  {
    path: '/game',
    name: 'Game',
    component: Game
  },
  {
    path: '/users',
    name: 'Users',
    component: UserAdmin
  },
  {
    path: '/mainGame',
    name: 'Main Game',
    component: MainGame,
    props: route => ({lobbyID: route.query.id})
  }
]

const router = new VueRouter({
  routes
})

export default router

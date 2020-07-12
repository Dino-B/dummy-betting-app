import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Tickets from '../views/Tickets.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/tickets',
      name: 'tickets',
      component: Tickets
    }
  ]
})
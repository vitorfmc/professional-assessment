import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home';
import store from './store';
import NoPower from './views/NoPower.vue';
import OneAOneList from './views/oneaone/OneAOneList';

Vue.use(Router);

function requireAuth(to, from, next) {
  let loggedUser = store.state.user.loggedUser;

  if (!loggedUser.isLogged) {
    next('/noPower');
  } else {
    next();
  }
}

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      breadcrumbs: [],
      breadcrumbName: 'Home'
    },
    {
      path: '/noPower',
      name: 'noPower',
      component: NoPower,
      breadcrumbs: [],
      breadcrumbName: 'Home'
    },
    {
      path: '/oneaone',
      name: 'oneaone',
      component: OneAOneList,
      beforeEnter: requireAuth,
      breadcrumbs: [],
      breadcrumbName: 'OneAOnes'
    }
  ]
});

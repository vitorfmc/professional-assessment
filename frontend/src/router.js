import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home';
import store from './store';
import NoPower from './views/NoPower.vue';
import GameList from './views/game/GameList';
import GameEdit from './views/game/GameEdit';
import GameCreate from './views/game/GameCreate';
import GameUploadPdf from './views/game/GameUploadPdf';

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
      path: '/game',
      name: 'game',
      component: GameList,
      beforeEnter: requireAuth,
      breadcrumbs: [],
      breadcrumbName: 'Jogos'
    },
    {
      path: '/game/create',
      name: 'gameCreate',
      component: GameCreate,
      beforeEnter: requireAuth,
      breadcrumbs: ['game'],
      breadcrumbName: 'Cadastrar Jogo'
    },
    {
      path: '/game/edit/:code',
      name: 'gameEdit',
      component: GameEdit,
      beforeEnter: requireAuth,
      breadcrumbs: ['game'],
      breadcrumbName: 'Editar Jogo'
    },{
      path: '/game/uploadPdf/:code',
      name: 'gameUploadPdf',
      component: GameUploadPdf,
      beforeEnter: requireAuth,
      breadcrumbs: ['game'],
      breadcrumbName: 'Upload de Manual'
    }
  ]
});

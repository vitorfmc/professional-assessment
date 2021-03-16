import Vue from 'vue';
import Vuex from 'vuex';

import user from './modules/user';
import alert from './modules/alert';

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== 'production';

export default new Vuex.Store({
  // state: {},
  // getters: {},
  // mutations: {},
  // actions: {},

  modules: {
    user,
    alert
  },
  strict: debug
});

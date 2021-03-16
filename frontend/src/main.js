import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store/';
import vuetify from './plugins/vuetify';

import filters from './filters';
filters.loadAll();

Vue.config.productionTip = false;

import packageJson from '../package.json';
process.env.VUE_APP_VERSION = packageJson.version;

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app');

<template>
  <v-app>
    <template>
      <PageHeader v-model="drawer" />
      <NavigationDrawer v-model="drawer" :items="navigationItems" />

      <v-content>
        <Alert />
        <router-view />
      </v-content>
    </template>
  </v-app>
</template>

<script>
import { mapActions, mapState } from 'vuex';

import PageHeader from '@/components/organisms/PageHeader.vue';
import NavigationDrawer from '@/components/organisms/NavigationDrawer.vue';
import Alert from '@/components/organisms/Alert.vue';

export default {
  name: 'App',
  components: { PageHeader, NavigationDrawer, Alert },

  data: () => ({
    drawer: true,
    navigationItems: [
      { title: 'Home', to: '/', icon: 'mdi-home' },
      {
        title: 'One a One',
        to: '/oneaone',
        icon: 'mdi-store',
        role: 'ROLE_USER'
      }
    ]
  }),

  computed: {
    ...mapState('user', ['loggedUser'])
  },

  created: async function() {
    await this.loadLocalUser();

    if (this.loggedUser && this.loggedUser.isLogged) {
      return;
    }

    if ('/oauth2/redirect' === this.$router.currentRoute.path) {
      const token = this.$router.currentRoute.query.token;
      await this.loadUser(token);
      this.$router.push({ name: 'home' });
    }
  },

  methods: {
    ...mapActions('user', ['loadUser', 'loadLocalUser'])
  }
};
</script>

<template>
  <v-app>
    <template>
      <PageHeader v-model="drawer" />
      <NavigationDrawer v-model="drawer" :items="this.navigationItems" />
      
      <v-content>
        <Alert />
        aaa{{navigationItems}}
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
      { title: 'One a One', to: '/oneaone', icon: 'mdi-store' }
    ]
  }),

  computed: {
    ...mapState('user', ['loggedUser'])
  },

  created: async function() {
    console.log('aaaaaaaaaaaaaaaaa');
    await this.loadLocalUser();
    console.log(this.loggedUser);

    if (this.loggedUser && this.loggedUser.isLogged) {
      return;
    }

    console.log('bbbbbbbbbbbbbbbb');
    if ('/oauth2/redirect' === this.$router.currentRoute.path) {
      console.log('cccccccccccccccc');
      const token = this.$router.currentRoute.query.token;
      console.log(token);
      await this.loadUser(token);
      console.log('ddddddddddddddddd');
    }

    console.log(this.loggedUser);
  },

  methods: {
    ...mapActions('user', ['loadUser', 'loadLocalUser'])
  }
};

</script>

<template>
  <v-card class="mx-auto">
    <v-row v-if="loggedUser.isLogged">
      <v-col align="center">
        <v-avatar color="grey" size="164">
          <v-img :src="loggedUser.imageUrl"></v-img>
        </v-avatar>
      </v-col>
      <v-col class="py-0">
        <v-list-item>
          <v-list-item-content class="py-0">
            <v-list-item-title class="title">
              {{ loggedUser.name }}
            </v-list-item-title>
            <v-list-item-subtitle>{{ loggedUser.username }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-col>
      <v-col class="py-0">
        <Logout :loading="loadingLogin" @logout="logout" />
      </v-col>
    </v-row>
    <v-row v-else>
      <v-col class="py-0">
        <GithubLogin />
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import { mapActions, mapState } from 'vuex';

import GithubLogin from '../molecules/login/GithubLogin.vue';
import Logout from '../molecules/login/Logout';

export default {
  components: {
    GithubLogin,
    Logout
  },

  data: () => ({
    loadingLogin: false
  }),

  computed: {
    ...mapState('user', ['loggedUser'])
  },

  methods: {
    ...mapActions('user', ['logoutUser']),

    logout: async function() {
      try {
        this.loadingLogin = true;
        await this.logoutUser();
        this.$router.push({ name: 'home' });
      } catch (e) {
        console.error('Erro ao deslogar', e);
      } finally {
        this.loadingLogin = false;
      }
    }
  }
};
</script>

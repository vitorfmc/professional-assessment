<template>
  <div>
    <div v-show="loggedUser.isLogged">
      <v-container style="padding:0rem !important; margin-top: 0.5rem">
        <v-list dense nav class="py-0">
          <v-list-item class="logout-btn" @click="logout">
            <v-list-item-icon>
              <v-icon left dark>mdi-logout</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Logout</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-container>
    </div>
    <div v-show="!loggedUser.isLogged" class="pb-10">
      <LoginForm v-model="user" :loading-login="loadingLogin" />
    </div>
    <div class="pb-10"></div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';
import LoginForm from '@/components/organisms/login/LoginForm.vue';

export default {
  components: {
    LoginForm
  },
  props: {},
  data: () => ({
    isLoading: false,
    loadingLogin: false,
    user: {
      username: null,
      password: null,
      errorMessage: null
    },
    dialog: false
  }),

  computed: {
    ...mapState('user', ['loggedUser'])
  },

  methods: {
    ...mapActions('user', ['logoutUser']),

    logout: async function() {
      try {
        await this.logoutUser();
        this.user.username = '';
        this.user.password = '';
        this.user.errorMessage = null;
        this.$router.push({ name: 'home' });
      } catch (e) {
        this.loadingLogin = false;
        this.user.errorMessage = 'Erro ao fazer logout';
      }
    }
  }
};
</script>

<style scoped>
.logout-btn {
  background-color: #9e7979 !important;
}
</style>

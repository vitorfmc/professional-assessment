<template>
  <div>
    <div v-show="loggedUser.isLogged">
      <v-container style="padding:0rem !important; margin-top: 0.5rem">
        <v-list dense nav class="py-0">
          <v-list-item class="logout-btn"
                @click="logout">
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
      <LoginForm
            v-model="user"
            :loadingLogin="loadingLogin"
            @login="login"
          />
    </div>
    <div class="pb-10"></div>
  </div>
</template>

<script>

import { mapActions, mapState, mapMutations } from 'vuex';
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
    ...mapState('user', ['loggedUser']),
  },

  async mounted() {
    this.loadLoggedUser();
  },

  methods: {
    ...mapActions('user', ['loadUser', 'logoutUser']),
    ...mapMutations('user', ['setLoggedUser']),

    async loadLoggedUser() {
      if(this.user.username != null){
        this.isLoading = true;
        this.dialog = false;
        await this.loadUser(this.user);
        this.isLoading = false;
      }
    },
    login: async function() {
      try{
        this.loadingLogin = true;
        await this.loadUser(this.user);
        this.user.errorMessage = null;
        this.loadingLogin = false;
        this.$router.push({ name: 'game' });
      }catch(e){
        console.log('error');
        this.loadingLogin = false;
        this.user.errorMessage = "Usuário e/ou senha inválido(a)"
      }
    },
    logout: async function() {
      try{
        await this.logoutUser();
        this.user.username = '';
        this.user.password = '';
        this.user.errorMessage = null;
        this.$router.push({ name: 'home' });
      }catch(e){
        this.loadingLogin = false;
        this.user.errorMessage = "Erro ao fazer logout"
      }
    }
  }
};
</script>

<style scoped>
  .logout-btn{
    background-color: #9e7979 !important;
  }
</style>
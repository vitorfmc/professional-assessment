<template>
  <v-container>
    <v-row justify="space-between">
      <v-col cols="12" md="12">
        <v-btn
          block
          color="primary"
          :loading="loading"
          :href="url"
          @click="click"
        >
          Log in com Github
          <v-icon right dark>
            mdi-github
          </v-icon>
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'GithubLogin',
  components: {},

  data: () => ({
    loading: false
  }),

  computed: {
    gitHubUrl: function() {
      return process.env.VUE_APP_GITHUB_LOGIN_URL;
    },
    gitHubRedirect: function() {
      return process.env.VUE_APP_GITHUB_LOGIN_REDIRECT;
    },
    url: function() {
      return this.gitHubUrl + '?redirect_uri=' + this.gitHubRedirect;
    }
  },

  methods: {
    click() {
      this.loading = true;
    }
  }
};
</script>

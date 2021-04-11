<template>
  <v-app-bar
    app
    collapse-on-scroll
    clipped-left
    style="background: #887c5b; color: white; font-weight: bold"
  >
    <v-app-bar-nav-icon @click="() => (drawer = !drawer)"></v-app-bar-nav-icon>

    <v-toolbar-title>
      <span>ONE A ONE</span>
    </v-toolbar-title>

    <v-spacer></v-spacer>
  </v-app-bar>
</template>

<script>
import { mapState } from 'vuex';

export default {
  components: {},

  props: {
    value: Boolean
  },

  data: () => ({}),

  computed: {
    ...mapState('user', ['loggedUser']),
    drawer: {
      get: function() {
        return this.value;
      },
      set: function(value) {
        this.$emit('input', value);
      }
    },
    darkMode: {
      get: function() {
        return this.$vuetify.theme.dark;
      },
      set: function(value) {
        this.$vuetify.theme.dark = value;
      }
    }
  },
  watch: {},
  methods: {}
};
</script>

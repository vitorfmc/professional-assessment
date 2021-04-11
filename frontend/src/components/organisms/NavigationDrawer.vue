<template>
  <v-navigation-drawer v-model="drawer" app clipped>
    <v-list dense nav class="py-0">
      <v-list-item>
        <v-list-item-content v-if="loggedUser.isLogged">
          <v-list-item-title>{{ loggedUser.username }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <template v-for="item in itemsUserCanSee">
        <v-list-item v-if="item.to" :key="item.to" :to="item.to">
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-group v-else :key="item.title" :prepend-icon="item.icon">
          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item-content>
          </template>

          <v-list-item
            v-for="child in item.children"
            :key="child.to"
            :to="child.to"
          >
            <v-list-item-icon />
            <v-list-item-title>{{ child.title }}</v-list-item-title>
          </v-list-item>
        </v-list-group>
      </template>
    </v-list>

    <SignIn />
  </v-navigation-drawer>
</template>

<script>
import { mapState } from 'vuex';

import SignIn from './login/SignIn';

export default {
  components: {
    SignIn
  },

  props: {
    value: Boolean,
    items: {
      type: Array,
      default: () => [], // ex: [{ title: 'Jogos', to: '/game', icon: 'mdi-store', role: 'ROLE_GAME_READ' }]
      required: true
    }
  },

  data: () => ({}),

  computed: {
    ...mapState('user', ['loggedUser']),
    itemsUserCanSee: function() {
      let newItems = [];

      this.items.forEach(item => {
        if (item.children) {
          let newItem = JSON.parse(JSON.stringify(item));

          newItem.children = newItem.children.filter(
            child => !child.role || this.loggedUser.hasRole(child.role)
          );
          if (newItem.children.length > 0) {
            newItems.push(newItem);
          }
        } else if (!item.role || this.loggedUser.hasRole(item.role)) {
          newItems.push(item);
        }
      });

      return newItems;
    },
    drawer: {
      get: function() {
        return this.value;
      },
      set: function(value) {
        this.$emit('input', value);
      }
    }
  },

  watch: {},

  methods: {
    logout: async function() {
      try {
        await this.logoutUser();
        this.$router.push({ name: 'home' });
      } catch (e) {
        this.loadingLogin = false;
        this.user.errorMessage = 'Erro ao fazer logout';
      }
    }
  }
};
</script>

<template>
  <v-navigation-drawer v-model="drawer" app clipped>
    <Avatar />

    <v-list dense nav>
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
  </v-navigation-drawer>
</template>

<script>
import { mapState } from 'vuex';

import Avatar from './Avatar';

export default {
  components: {
    Avatar
  },

  props: {
    value: Boolean,
    items: {
      type: Array,
      default: () => [], // ex: [{ title: 'Jogos', to: '/game', icon: 'mdi-store', role: 'ROLE_GAME_READ' }]
      required: true
    }
  },

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
  }
};
</script>

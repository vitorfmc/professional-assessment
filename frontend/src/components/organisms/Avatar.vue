<template>
  <v-card class="mx-auto">
    <v-row>
      <v-col align="center">
        <v-avatar color="grey" size="164">
          <v-img :src="user.imageUrl"></v-img>
        </v-avatar>
      </v-col>
      <v-col class="py-0">
        <v-list-item>
          <v-list-item-content class="pt-0">
            <v-list-item-title class="title">
              {{ user.name }}
            </v-list-item-title>
            <v-list-item-subtitle>{{ user.username }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
export default {
  components: {},

  props: {
    user: {
      type: Object,
      required: true
    }
  },

  data: () => ({})
};
</script>

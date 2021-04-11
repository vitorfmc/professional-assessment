<template>
  <div>
    <v-container>
      <v-row justify="space-between">
        <v-col cols="12" md="12">
          <v-btn block color="primary" :loading="loading" @click="click">
            Logout
            <v-icon right dark>
              mdi-logout
            </v-icon>
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  props: {
    loading: {
      type: Boolean,
      default: false,
      required: false
    }
  },
  methods: {
    click: async function() {
      this.$emit('logout');
    }
  }
};
</script>

<style scoped></style>

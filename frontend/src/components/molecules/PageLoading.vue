<template>
  <v-container grid-list-xl>
    <v-layout column>
      <v-flex align-self-center>
        <v-progress-circular
          :size="50"
          color="primary"
          indeterminate
        ></v-progress-circular>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  components: {},
  props: {},
  data: () => ({})
};
</script>

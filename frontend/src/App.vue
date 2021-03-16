<template>
  <v-app>
    <template>
      <PageHeader v-model="drawer" />
      <NavigationDrawer v-model="drawer" />
      <v-content>
        <Alert />
        <router-view />
      </v-content>
    </template>
  </v-app>
</template>

<script>
import PageHeader from '@/components/organisms/PageHeader.vue';
import NavigationDrawer from '@/components/organisms/NavigationDrawer.vue';
import Alert from '@/components/organisms/Alert.vue';

export default {
  name: 'App',
  components: { PageHeader, NavigationDrawer, Alert },

  data: () => ({
    drawer: true
  })
};

</script>

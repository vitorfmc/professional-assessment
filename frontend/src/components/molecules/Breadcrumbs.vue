<template>
  <v-container grid-list-xl>
    <v-layout column>
      <v-flex>
        <v-breadcrumbs
          style="padding: 0;"
          large
          :items="breadcrumbs"
          divider="/"
        ></v-breadcrumbs>
      </v-flex>
      <slot></slot>
      <v-divider></v-divider>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  components: {},
  props: {},

  data: () => ({}),

  computed: {
    currentBreadcrumb: function() {
      const currentRoute = this.$router.currentRoute;
      const optionsRoutes = this.$router.options.routes;
      const optionsCurrentRoute = optionsRoutes.find(
        r => r.name === currentRoute.name
      );

      return {
        text: optionsCurrentRoute.breadcrumbName,
        to: currentRoute.path
      };
    },
    parentBreadcrumbs: function() {
      const currentRoute = this.$router.currentRoute;
      const optionsRoutes = this.$router.options.routes;

      const optionsCurrentRoute = optionsRoutes.find(
        r => r.name === currentRoute.name
      );
      const optionsCurrentRouteBreadcrumbs = optionsCurrentRoute.breadcrumbs;

      return optionsCurrentRouteBreadcrumbs.map(breadcrumbName => {
        const route = optionsRoutes.find(r => r.name === breadcrumbName);
        return {
          text: route.breadcrumbName,
          to: route.path
        };
      });
    },
    breadcrumbs: function() {
      return [...this.parentBreadcrumbs, this.currentBreadcrumb];
    }
  }
};
</script>

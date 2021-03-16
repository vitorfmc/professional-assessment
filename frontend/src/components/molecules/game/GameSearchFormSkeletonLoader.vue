<template>
  <v-expansion-panels>
    <v-expansion-panel style="background-color:#ccc7bb">
      <v-expansion-panel-header>PESQUISA</v-expansion-panel-header>
      <v-expansion-panel-content>
        <v-form ref="form">
            <v-container>
              <v-row>
                <v-col cols="12" md="4" style="height: 94px;">
                  <v-skeleton-loader type="text"></v-skeleton-loader>
                </v-col>
                <v-col cols="12" md="4" style="height: 94px;">
                  <v-skeleton-loader type="text"></v-skeleton-loader>
                </v-col>
                <v-col cols="12" md="4" style="height: 94px;">
                  <v-skeleton-loader type="text"></v-skeleton-loader>
                </v-col>
                <v-col cols="12" md="4" style="height: 94px;">
                  <v-skeleton-loader type="text"></v-skeleton-loader>
                </v-col>
                <v-col cols="12" md="4" style="height: 94px;">
                  <v-skeleton-loader type="text"></v-skeleton-loader>
                </v-col>
                <v-col cols="12" md="4" style="height: 94px;">
                  <v-skeleton-loader type="text"></v-skeleton-loader>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script>

export default {
  name: 'GameSearchFormSkeletonLoader',
  components: {},

  props: {},

  data() {
    return {
      show: false
    };
  },

  computed: {},

  watch: {},

  methods: {}
};
</script>

<style scoped></style>
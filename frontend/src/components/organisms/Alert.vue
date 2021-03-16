<template>
  <div
    v-if="dataAlerts.length > 0"
    style="position: absolute; right: 0px; min-width: 20%; "
    class="flex md6 sm12 ma-md-3 ma-sm-2 ma-1"
  >
    <v-alert
      v-for="alert in dataAlerts"
      :key="alert.id"
      :type="alert.type"
      style="z-index: 1000"
      dismissible
      @input="dismiss(alert.id)"
    >
      <span @mouseover="alert.hover = true" @mouseleave="alert.hover = false">
        {{ alert.message }}
      </span>

      <template slot="close">
        <div style="margin-left: 16px;">
          <BtnTimer
            x-small
            only-ico
            btn-ico="mdi-close-circle"
            btn-ico-color="white"
            :override-ico-size="24"
            :stop="alert.hover"
            :toggle="() => dismiss(alert.id)"
          />
        </div>
      </template>
    </v-alert>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex';

import BtnTimer from '@/components/molecules/BtnTimer.vue';

export default {
  components: {
    BtnTimer
  },

  props: {},

  data: () => ({
    dataAlerts: []
  }),

  computed: {
    ...mapState('alert', ['alerts'])
  },

  watch: {
    alerts() {
      this.dataAlerts = this.alerts.map(alert => {
        return Object.assign({}, alert, { hover: false });
      });
    }
  },

  beforeMount() {
    this.dataAlerts = this.alerts.map(alert => {
      return Object.assign({}, alert, { hover: false });
    });
  },

  methods: {
    ...mapMutations('alert', ['removeMessageById']),
    dismiss(id) {
      this.removeMessageById(id);
    }
  }
};
</script>

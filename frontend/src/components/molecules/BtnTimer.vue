<template>
  <v-btn
    fab
    :text="onlyIco"
    :x-small="xSmallData"
    :small="smallData"
    :large="largeData"
    :x-large="xLargeData"
    :color="btnColor"
    @click="toggle"
  >
    <v-progress-circular
      :color="timerColor"
      :rotate="360"
      :size="overrideIcoSize || timerSize"
      :width="timerWidth"
      :value="timer"
    >
      <v-icon :size="overrideIcoSize" :color="btnIcoColor">{{ btnIco }}</v-icon>
    </v-progress-circular>
  </v-btn>
</template>

<script>
/**
 * Componente de butao com um timer para acionar automaticamente depois de x segundos.
 */
export default {
  components: {},

  props: {
    toggle: {
      type: Function,
      required: true
    },
    stop: {
      type: Boolean,
      default: false
    },
    timerInterval: {
      type: Number,
      default: 1000
    },
    timerIncrease: {
      type: Number,
      default: 5
    },
    onlyIco: {
      type: Boolean,
      default: false
    },
    overrideIcoSize: {
      type: Number,
      default: undefined
    },
    btnColor: {
      type: String,
      default: 'white'
    },
    btnIcoColor: {
      type: String,
      default: 'error'
    },
    btnIco: {
      type: String,
      default: 'mdi-close'
    },
    timerColor: {
      type: String,
      default: 'black'
    },
    xSmall: {
      type: Boolean,
      default: false
    },
    small: {
      type: Boolean,
      default: false
    },
    large: {
      type: Boolean,
      default: false
    },
    xLarge: {
      type: Boolean,
      default: false
    }
  },

  data: function() {
    let data = {
      timer: 0,
      interval: undefined,
      xSmallData: this.xSmall,
      smallData: this.small,
      largeData: this.large,
      xLargeData: this.xLarge
    };

    if (!this.xSmall && !this.small && !this.large && !this.xLarge) {
      data.smallData = true;
    }

    if (data.xSmallData) {
      data.timerWidth = 3;
      data.timerSize = undefined;
    } else if (data.smallData) {
      data.timerWidth = 5;
      data.timerSize = 40;
    } else if (data.largeData) {
      data.timerWidth = 5;
      data.timerSize = 64;
    } else if (data.xLargeData) {
      data.timerWidth = 7;
      data.timerSize = 72;
    }

    return data;
  },

  computed: {},

  mounted() {
    this.interval = setInterval(() => {
      if (this.stop) return;
      this.timer += this.timerIncrease;
      if (this.timer >= 100) {
        this.timer = 0;
        this.toggle();
      }
    }, this.timerInterval);
  },

  beforeDestroy() {
    clearInterval(this.interval);
  },

  methods: {}
};
</script>

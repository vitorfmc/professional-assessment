<template>
  <v-row no-gutters>
    <v-col cols="2" lg="1">{{ dataValue }}</v-col>
    <v-col cols="10" lg="11">
      <v-slider
        v-model="dataValue"
        light
        :max="max"
        :min="min"
        hide-details
        :thumb-label="thumbLabel"
        @change="change"
      ></v-slider>
    </v-col>
  </v-row>
</template>

<script>
export default {
  components: {},
  props: {
    value: {
      required: true
    },
    lazy: {
      type: Boolean,
      default: false
    },
    thumbLabel: {
      default: false
    },
    min: {
      type: Number,
      default: 0
    },
    max: {
      type: Number,
      default: 10
    }
  },

  data() {
    return {
      dataValue: this.value
    };
  },

  watch: {
    dataValue: function(newValue) {
      if (!this.lazy) {
        this.$emit('input', newValue);
      }
    }
  },

  methods: {
    change: function(newValue) {
      if (this.lazy) {
        this.$emit('input', newValue);
      }
    }
  }
};
</script>

<style lang="scss" scoped></style>

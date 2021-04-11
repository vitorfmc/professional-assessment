<template>
  <v-card v-if="!oldLayout">
    <v-toolbar flat>
      <v-toolbar-title class="primary--text">{{ title }}</v-toolbar-title>

      <v-spacer></v-spacer>

      <transition v-if="withSeach" name="search-card">
        <v-text-field
          v-if="searchFocus || search"
          v-model="search"
          clearable
          flat
          autofocus
          single-line
          hide-details
          prepend-inner-icon="mdi-magnify"
          label="Search"
          @focus="() => (searchFocus = true)"
          @blur="() => (searchFocus = false)"
        ></v-text-field>
      </transition>

      <v-btn
        v-if="withSeach && !(searchFocus || search)"
        icon
        color="primary"
        @click="() => (searchFocus = true)"
      >
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
      <slot name="actions"></slot>
    </v-toolbar>

    <v-divider></v-divider>

    <slot></slot>
  </v-card>

  <v-card
    v-else
    :class="leftTitle ? 'card-with-left-title' : 'card-without-left-title'"
  >
    <div v-if="leftTitle" class="card-left-title">{{ leftTitle }}</div>

    <v-sheet tile color="white--text primary" class="card-title">{{
      title
    }}</v-sheet>

    <v-sheet tile class="card-content" :min-height="minHeight">
      <slot></slot>
    </v-sheet>
  </v-card>
</template>

<script>
import debounce from 'debounce';

export default {
  components: {},
  props: {
    oldLayout: Boolean,
    title: {
      type: String,
      required: true
    },
    leftTitle: String, // old layout
    minHeight: String, // old layout
    withSeach: Boolean // new layout
  },

  data: () => ({
    search: '',
    searchFocus: false
  }),

  watch: {
    search: function() {
      this.searchChange();
    }
  },

  methods: {
    searchChange: debounce(function() {
      this.$emit('search', this.search);
    }, 500)
  }
};
</script>

<style lang="scss" scoped>
/////////////////// new layout
.search-card-enter-active {
  transition: all 0.3s;
}
.search-card-leave-active {
  transition: all 0.8s;
}
.search-card-enter, .search-card-leave-to
/* .slide-fade-leave-active em versões anteriores a 2.1.8 */ {
  /* transform: matrix(1.0, 2.0, 3.0, 4.0, 5.0, 6.0); */
  /* transform: translate(12px, 50%); */
  /* transform: translateX(2em); */
  /* transform: translateY(3in); */
  /* transform: scale(2, 0.5); */
  /* transform: scaleX(2); */
  /* transform: scaleY(0.5); */
  /* transform: rotate(0.5turn); */
  /* transform: skewX(30deg); */
  /* transform: skewY(1.07rad); */
  /* transform: matrix3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0); */
  /* transform: translate3d(12px, 50%, 3em); */
  /* transform: translateZ(2px); */
  /* transform: scale3d(2.5, 1.2, 0.3); */
  /* transform: scaleZ(0.3); */
  /* transform: rotate3d(1, 2.0, 3.0, 10deg); */
  /* transform: rotateX(10deg); */
  /* transform: rotateY(10deg); */
  /* transform: rotateZ(10deg); */
  /* transform: perspective(17px); */
  transform: translateX(400px) scaleX(0);
  /* transform: translateX(1000px); */
  /* opacity: 0; */
}

/////////////////// old layout
.card-without-left-title {
  // .card-content,
  .card-title {
    padding: 10px 15px;
  }
}

.card-with-left-title {
  border-left: 52px solid var(--v-secondary-base);

  .card-title {
    color: #fff;
    padding: 10px 15px;
  }
  .card-content {
    // padding: 10px;
    overflow-x: auto;
    overflow-y: hidden;
    white-space: nowrap;
  }

  .card-left-title {
    padding: 15px 15px;
    position: absolute;
    left: 0;
    transform-origin: 0 0;
    transform: rotate(90deg);
  }
}
</style>

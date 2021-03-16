import Vue from 'vue';
import Vuetify from 'vuetify/lib';

import colors from 'vuetify/lib/util/colors';

Vue.use(Vuetify);

export default new Vuetify({
  icons: {
    iconfont: 'mdiSvg'
  },
  theme: {
    // dark: true,
    themes: {
      light: {
        primary: "#887c5b",
        secondary: colors.blue.lighten4,
        'card-grey': colors.grey.lighten3
      }
    },
    options: {
      customProperties: true
    }

    // themes: {
    //   light: {
    //     primary: colors.purple,
    //     secondary: colors.grey.darken1,
    //     accent: colors.shades.black,
    //     error: colors.red.accent3,
    //   },
    //   dark: {
    //     primary: '#1976D2',
    //     secondary: '#424242',
    //     accent: '#82B1FF',
    //     error: '#FF5252',
    //     info: '#2196F3',
    //     success: '#4CAF50',
    //     warning: '#FFC107',
    //   },
    // },
  }
});

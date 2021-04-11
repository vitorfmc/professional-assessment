<template>
  <v-text-field
    v-if="fake"
    value="FakePasswordUhul"
    append-icon="mdi-pencil"
    type="password"
    :label="label"
    hint="Pelo menos 8 caracteres"
    readonly
    @click:append="fake = !fake"
  ></v-text-field>
  <v-text-field
    v-else
    v-model="password"
    :append-icon="appendIcon"
    :rules="[rules.required, rules.min]"
    :type="show ? 'text' : 'password'"
    :label="label"
    hint="Pelo menos 8 caracteres"
    counter
    @click:append="appendClick"
  ></v-text-field>
</template>

<script>
/**
 * Componente de password com base no v-text-field do Vuetify.
 * Criado para encapsular as regras de negocio e logicas do componente.
 */
export default {
  name: 'Password',
  components: {},

  props: {
    value: {
      required: true
    },
    label: {
      type: String,
      required: true
    },
    fakeBeforeEdit: {
      type: Boolean,
      default: false
    }
  },

  data() {
    return {
      password: this.value,
      show: false,
      fake: this.fakeBeforeEdit,
      rules: {
        required: v => !!v || 'Obrigatório.',
        min: v => (!!v && v.length >= 8) || 'Min 8 caracteres'
      }
    };
  },

  computed: {
    canFakePassword: function() {
      return this.fakeBeforeEdit && this.password === '' && !this.show;
    },
    appendIcon: function() {
      if (this.canFakePassword) {
        return this.fake ? 'mdi-pencil' : 'mdi-pencil-off';
      }

      return this.show ? 'mdi-eye' : 'mdi-eye-off';
    }
  },

  watch: {
    password: function(value) {
      this.$emit('input', value);
      if (value === '') {
        this.show = false;
      }
    }
  },

  methods: {
    appendClick: function() {
      if (this.canFakePassword) {
        this.fake = !this.fake;
      } else {
        this.show = !this.show;
      }
    }
  }
};
</script>

<style scoped></style>

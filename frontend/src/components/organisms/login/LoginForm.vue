<template>
  <v-form ref="form" v-model="valid">
    <v-container>
      <v-row>
        <v-col cols="12" md="12">
          <span class="red--text caption">{{data.errorMessage}}</span>
        </v-col>
        <v-col cols="12" md="12">
          <v-text-field
            filled
            class="lowercase"
            v-model="data.username"
            label="Usuário"
          ></v-text-field>
        </v-col>
        <v-col cols="12" md="12">
          <v-text-field
            filled
            v-model="data.password"
            type="password"
            label="Senha"
          ></v-text-field>
        </v-col>
      </v-row>

       <v-row justify="space-between">
        <v-col cols="12" md="12">
          <v-btn
            block
            :loading="loadingLogin"
            color="primary"
            @click="login">
            ENTRAR
          </v-btn>
        </v-col>
      </v-row>

    </v-container>
  </v-form>
</template>

<script>

export default {
  name: 'LoginForm',
  components: {},

  props: {
    value: {
      type: Object,
      required: true
    },
    loadingLogin: {
      type: Boolean,
      default: false
    },
  },

  data() {
    return {
      valid: false,
      data: {
        errorMessage: null,
        username: '',
        password: null,
      },
    };
  },

  computed: {
    
  },

  watch: {
    data: {
      handler: function(value) {
        this.$emit('input', value);
      },
      deep: true
    }
  },

  methods: {
    validate() {
      if(this.data.username == null || this.data.username == null){
        this.data.errorMessage = 'Usuário e senha obrigatórios'
          this.valid = false
      }else{
        this.data.errorMessage = null;
        this.valid = true
      }

      return this.$refs.form.validate();
    },
    login() {
      this.validate();
      if (this.valid) {
        this.$emit('login', this.data);
      }
    }
  }
};
</script>
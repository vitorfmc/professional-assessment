<template>
  <v-form ref="form" v-model="valid">
    <v-container>
      <v-row>
        <v-col cols="12" md="12">
          <span class="red--text caption">{{data.errorMessage}}</span>
        </v-col>
      </v-row>
      <v-row justify="space-between">
        <v-col cols="12" md="12">
          <v-btn
            block
            color="primary"
            href="http://localhost:8080/oauth2/authorize/github?redirect_uri=http://localhost:8081/oauth2/redirect"
          >
            Log in com Github
            <v-icon right dark>
              mdi-github
            </v-icon>
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
    }
  }
};
</script>
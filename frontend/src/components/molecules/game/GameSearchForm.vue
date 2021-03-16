<template>
  <v-expansion-panels>
    <v-expansion-panel style="background-color:#ccc7bb">
      <v-expansion-panel-header>PESQUISA</v-expansion-panel-header>
      <v-expansion-panel-content>
        <v-form ref="form">
          <v-container>
            <v-row>
              <v-col cols="12" md="4">
                <v-text-field
                  filled
                  v-model="data.name"
                  :disabled="loading"
                  label="Nome ou Código"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="4">
                <v-combobox
                    filled
                    v-model="data.type"
                    :items="typesList"
                    :return-object="false"
                    :disabled="loading"
                    :loading="typesLoading"
                    item-value="id"
                    item-text="name"
                    label="Tipo"
                  ></v-combobox>
              </v-col>
              <v-col cols="12" md="4">
                <v-combobox
                    filled
                    multiple
                    v-model="data.categories"
                    :items="categoriesList"
                    :return-object="false"
                    :disabled="loading"
                    :loading="categoriesLoading"
                    item-value="id"
                    item-text="name"
                    label="Categorias"
                  ></v-combobox>
              </v-col>
              <v-col cols="12" md="3">
                <v-combobox
                    filled
                    v-model="data.qtdPlayers"
                    :items="qtdPlayersList"
                    :return-object="false"
                    :disabled="loading"
                    item-value="id"
                    item-text="name"
                    label="Jogadores"
                  ></v-combobox>
              </v-col>
              <v-col cols="12" md="2">
                <v-combobox
                    filled
                    v-model="data.age"
                    :items="ageList"
                    :return-object="false"
                    :disabled="loading"
                    item-value="id"
                    item-text="name"
                    label="Idade"
                  ></v-combobox>
              </v-col>
              <v-col cols="12" md="3">
                <v-combobox
                    filled
                    v-model="data.playTime"
                    :items="playTimeList"
                    :return-object="false"
                    :disabled="loading"
                    item-value="id"
                    item-text="name"
                    label="Tempo de jogo"
                  ></v-combobox>
              </v-col>
              <v-col cols="12" md="2">
                <v-btn
                    :loading="loading"
                    color="red"
                    @click="cleanFilter"
                    style="min-height: 68%; width: 100%"
                  >
                    Limpar
                  </v-btn>
              </v-col>
              <v-col cols="12" md="2">
                <v-btn
                    :loading="loading"
                    color="primary"
                    @click="search"
                    style="min-height: 68%; width: 100%"
                  >
                    PESQUISAR
                  </v-btn>
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script>
import AutoComplete from '@/components/molecules/AutoComplete.vue';

export default {
  name: 'GameSearchForm',
  components: {
    AutoComplete
  },

  props: {
    loading: {
      type: Boolean,
      default: false
    },
    typesList: {
      type: Array,
      default: () => []
    },
    typesLoading: {
      type: Boolean
    },
    categoriesList: {
      type: Array,
      default: () => []
    },
    categoriesLoading: {
      type: Boolean
    },
  },

  data() {
    return {
      data: {
        name: null,
        type: null,
        categories: [],
        qtdPlayers: null,
        playTime: null,
        age: null,
      },
      show: false,
      qtdPlayersList: [
        {id:"select", name:"Selecione"},
        {id:"2", name:"2+"},
        {id:"3", name:"3+"},
        {id:"4", name:"4+"},
        {id:"5", name:"5+"},
        {id:"6", name:"6+"},
        {id:"7", name:"7+"}
      ],
      playTimeList: [
        {id:"select", name:"Selecione"},
        {id:"15", name:"<= 15 min"},
        {id:"30", name:"<= 30 min"},
        {id:"60", name:"<= 1 hr"},
        {id:"120", name:"<= 2 hrs"},
      ],
      ageList: [
        {id:"select", name:"Selecione"},
        {id:"5", name:"5+"},
        {id:"8", name:"8+"},
        {id:"10", name:"10+"},
        {id:"12", name:"12+"},
        {id:"14", name:"14+"},
      ]
    };
  },

  computed: {},

  watch: {
    data: {
      handler: function(value) {
        this.$emit('input', value);
      },
      deep: true
    }
  },

  methods: {
    cleanFilter(){
      this.data = {
        name: null,
        type: null,
        categories: [],
        qtdPlayers: null,
        playTime: null,
        age: null,
      };
    },
    search() {
      this.$emit('search', this.data);
    },
  }
};
</script>

<style scoped></style>
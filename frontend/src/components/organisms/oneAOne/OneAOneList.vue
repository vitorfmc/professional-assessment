<template>
  <v-card>
    <v-card-title>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Buscar"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>

    <v-data-table 
      :headers="headers"
      :items="list"
      :loading="loading"
      loading-text="Carregando..."
      :page.sync="page"
      :items-per-page="itemsPerPage"
      hide-default-footer
      @page-count="pageCount = $event"
      :search="search">
      <template v-slot:no-data>
          Nenhum resultado encontrado.
      </template>
      <template v-slot:no-results>
          Nenhum resultado encontrado.
      </template>
      <template v-slot:item.statusDescription="{ item }">
        <v-chip :color="getColor(item.statusDescription)" dark>{{ item.statusDescription }}</v-chip>
      </template>
      <template v-slot:item.description="{ item }">
        <v-btn text color="primary" @click="edit(item.code)">
          {{ item.description }}  
        </v-btn> 
      </template>
     
    </v-data-table>

    <div class="text-center pt-2">
      <v-pagination v-model="page" :total-visible="10" :length="pageCount"></v-pagination>
    </div>
  </v-card>
</template>

<script>
export default {
  name: 'OneAOneList',
  components: {},
  props: {
    loading: {
      type: Boolean
    },
    list: {
      type: Array
    },
    canEdit: {
      type: Boolean,
      required: true
    }
  },
  data: () => ({
    search: '',
    itemsPerPage: 10,
    pageCount: 1,
    page: 1,
    headers: [
      { text: 'Id', align: 'start', value: 'id' },
      { text: 'Criado', align: 'start', value: 'creatorAssessments.' },
      { text: 'Participante', align: 'start',value: 'storeType' },
    ],
  }),
  computed: {},
  methods: {
    getColor (statusDescription) {
      if (statusDescription == "Ativa") return 'green'
      else return 'red'
    },
    edit: function(itemId) {
      if(this.canEdit) {
        this.$emit('editInfo', itemId);
      }
    }
  }
};
</script>

<style scoped></style>
<template>
  <v-container grid-list-xl>
    <v-layout column>
      <Breadcrumbs />
      <v-flex>
        <Card title="One-a-One">
          <v-row justify="center">
            <v-col md="12">
              <oneAOneList
                :loading="pageLoading"
                :list="oneAOneList"
                @editInfo="editInfo"/>
            </v-col>
          </v-row>
        </Card>

        <v-btn fab bottom right fixed color="primary" @click="create">
          <v-icon>mdi-plus</v-icon>
        </v-btn>

      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex';
import Breadcrumbs from '@/components/molecules/Breadcrumbs.vue';
import Card from '@/components/molecules/Card.vue';
import OneAOneList from '@/components/organisms/oneAOne/OneAOneList.vue';
import oneAOneApi from '@/api/oneAOneApi';
export default {
  name: 'OneAOneList',
  components: {
    Breadcrumbs,
    Card,
    OneAOneList
  },
  data: () => ({
    pageLoading: true,
    oneAOneList: [],
  }),
  computed: {
    ...mapState('user', ['loggedUser']),
  },
  watch: {
    page: function(val, oldVal) {
      this.loadList();
    }
  },
  mounted: function() {
    this.loadList();
  },
  methods: {
    ...mapActions('user', ['logoutUser']),
    loadList: async function() {
      this.pageLoading = true;
      try{
        const result = await oneAOneApi.findAll(this.page - 1, this.perPage, this.loggedUser);
        this.oneAOneList = result.content;
      
      } catch(e) {
        try{
          await this.logoutUser();
          this.errorMsg = null;
          this.$router.push({ name: 'home' });
        
        }catch(e){
          this.errorMsg = "Erro ao fazer logout"
        }
      
      } finally {
        this.pageLoading = false;
      }
    },
    editInfo: function(code) {
      this.$router.push({ name: 'oneAOneEdit', params: { id: id } });
    },
    create: function() {
      this.$router.push({ name: 'oneAOneCreate' });
    },
  }
};
</script>

<style scoped></style>
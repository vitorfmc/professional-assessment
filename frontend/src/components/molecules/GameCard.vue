<template>
  <v-card
    class="mx-auto"
    max-width="400"
  >
    <v-img v-if="item.image == null || item.image.type == null"
        v-bind:style="{'width': '100%', 'height': '318px'}"
        :src="require('../../assets/no-image.png')"
    />
    <img v-else 
        v-bind:style="{'width': '100%', 'height': '310px'}"
        :src="`data:${item.image.type};base64,${item.image.base64}`"
    />

    <v-card-text class="text--primary">
      <v-expansion-panels>
        <v-expansion-panel>
          <v-expansion-panel-header v-if="item.title == null">
            <span v-if="item.nameFormatted.length > 32">{{item.nameFormatted.substring(0,32)}}...</span>
            <span v-else>{{item.nameFormatted}}</span>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-expansion-panels>
              <v-expansion-panel>
                <v-expansion-panel-header>Descrição</v-expansion-panel-header>
                <v-expansion-panel-content>
                  <div style="white-space: pre-line;">{{item.description}}</div>
                </v-expansion-panel-content>
              </v-expansion-panel>
              <v-expansion-panel>
                <v-expansion-panel-header>Detalhes</v-expansion-panel-header>
                <v-expansion-panel-content>
                  <v-row>
                    <v-col cols="12" md="6" class="font-weight-bold">Nome:</v-col>
                    <v-col cols="12" md="6">{{item.name}}<span v-if="item.subtitle != null && item.subtitle.length > 1">:<br>{{item.subtitle}}</span></v-col>
                  </v-row>  
                  <v-row>
                    <v-col cols="12" md="6" class="font-weight-bold">Tipo:</v-col>
                    <v-col cols="12" md="6">{{item.type}}</v-col>
                  </v-row>  
                  <v-row>
                    <v-col cols="12" md="6" class="font-weight-bold">Jogadores:</v-col>
                    <v-col cols="12" md="6">{{item.numberOfPlayerComplement}}<br>Melhor com: {{item.numberOfPlayersBestWith}}</v-col>
                  </v-row>  
                  <v-row>
                    <v-col cols="12" md="6" class="font-weight-bold">Expansões:</v-col>
                    <v-col cols="12" md="6">
                      <span v-if="item.expansions.length <= 0">Nenhuma</span>
                      <div v-else v-for="expansion in item.expansions" :key="expansion">
                        {{expansion}},
                      </div>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12" md="6" class="font-weight-bold">Categorias:</v-col>
                    <v-col cols="12" md="6">
                      <span v-if="item.categories.length <= 0">Nenhuma</span>
                      <div v-else v-for="category in item.categories" :key="category">
                        {{category}},
                      </div>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12" md="6" class="font-weight-bold">Idade:</v-col>
                    <v-col cols="12" md="6">+{{item.minAge}}</v-col>
                  </v-row>  
                  <v-row>
                    <v-col cols="12" md="6" class="font-weight-bold">Duração:</v-col>
                    <v-col cols="12" md="6">{{item.durationInMinutesComplement}}</v-col>
                  </v-row>  
                </v-expansion-panel-content>
              </v-expansion-panel>
              <v-expansion-panel>
                <v-expansion-panel-header>Links</v-expansion-panel-header>
                <v-expansion-panel-content>
                  <div v-for="link in item.links" class="mt-1">
                    <a :href="link" target="_blank">{{link.substring(0, 30)}}...</a>
                  </div>
                  <span v-if="typeof item.links === 'undefined' || item.links === null || item.links.length <= 0">Nenhum cadastrado.</span>
                </v-expansion-panel-content>
              </v-expansion-panel>
              <v-expansion-panel>
                <v-expansion-panel-header>Aquivos</v-expansion-panel-header>
                <v-expansion-panel-content>
                  <div v-for="manual in item.manuals" class="mt-1">
                    <a href="javascript: void(0)" @click="manualDownload(item.code, manual.name)">{{manual.name}}</a>
                  </div>
                  <span v-if="typeof item.manuals == 'undefined' || item.manuals == null || item.manuals.length <= 0">Nenhum cadastrado.</span>
                </v-expansion-panel-content>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-card-text>

    <v-card-actions v-if="canEdit || canUploadPdf" class="text-end">
      <v-btn
        v-if="canEdit"
        color="orange"
        text
        class="v-messages float-right"
        @click="edit(item.code)"
      >
        EDITAR
      </v-btn>
      <v-btn
        v-if="canUploadPdf"
        color="orange"
        text
        class="v-messages float-right"
        @click="uploadData(item.code)"
      >
        UPLOAD PDF
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'GameCard',
  components: {},

  props: {
    loading: {
      type: Boolean
    },
    item: {
      type: Object
    },
    canEdit: {
      type: Boolean,
      required: true
    },
    canView: {
      type: Boolean,
      required: true
    },
    canUploadPdf: {
      type: Boolean,
      required: true
    }
  },

  data: () => ({
  }),

  computed: {
    ...mapState('user', ['loggedUser']),
  },

  methods: {
    edit: function(code) {
      if(this.canEdit) {
        this.$emit('editInfo', code);
      }
    },
    uploadData: function(code) {
      if(this.canUploadPdf) {
        this.$emit('uploadData', code);
      }
    },
    manualDownload: function(gameCode, manualName) {
      this.$emit('manualDownload', gameCode, manualName);
    }
  }
};
</script>

<style scoped></style>

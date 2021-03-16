<template>
  <v-autocomplete
    v-if="dataCanAdd"
    v-model="dataValue"
    :disabled="disabled"
    :items="itemsWithNew"
    chips
    :label="label"
    multiple
    deletable-chips
    clearable
    :item-text="itemText"
    :item-value="itemValue"
    :rules="rules"
    :loading="loading"
    @update:search-input="autocompleteSearch"
  >
  </v-autocomplete>

  <v-autocomplete
    v-else
    v-model="dataValue"
    :return-object="returnObject"
    :disabled="disabled"
    :items="dataItems"
    chips
    :label="label"
    multiple
    deletable-chips
    clearable
    :item-text="itemText"
    :item-value="itemValue"
    :rules="rules"
    :loading="loading"
  >
  </v-autocomplete>
</template>

<script>
/**
 * Componente de autoComplete com base no v-autocomplete do Vuetify.
 * Criado para ter a possibilidade de adicionar um novo valor na lista onthefly,
 * para isso basta marcar o componente com 'can-add'.
 *
 * OBS: Funcionalidade de adicionar novo valor só funciona com listas de String,
 * lista de Objetos não é suportado. Caso seja passado uma lista de OBJ e marcado
 * com 'can-add', o mesmo é desabilitado.
 */
export default {
  name: 'AutoComplete',
  components: {},

  props: {
    value: {
      required: true
    },
    items: {
      type: Array,
      required: true
    },
    label: {
      type: String,
      required: true
    },
    canAdd: {
      type: Boolean,
      default: false
    },
    returnObject: {
      type: Boolean,
      default: true
    },
    disabled: {
      type: Boolean
    },
    itemText: {
      type: String,
      default: 'name'
    },
    itemValue: {
      type: String,
      default: 'value'
    },
    rules: {
      type: Array
    },
    loading: {
      type: Boolean
    }
  },

  data() {
    return {
      dataValue: this.value,
      dataCanAdd: this.canAdd,
      newItem: '',
      newItems: []
    };
  },

  computed: {
    itemsWithNew: function() {
      if (this.newItem === '') {
        return this.dataItems;
      }

      return [
        ...this.dataItems,
        this.toObj(this.newItem, 'Novo: ' + this.newItem)
      ];
    },
    itemsIsObjs: function() {
      return this.items.length > 0 && this.items[0] instanceof Object;
    },
    dataItems: function() {
      let dataItems;

      if (this.dataCanAdd && !this.itemsIsObjs) {
        dataItems = this.items.map(i => this.toObj(i, i));
      } else {
        dataItems = [...this.items];
      }

      return [...dataItems, ...this.newItems];
    }
  },

  watch: {
    dataValue: function(value) {
      this.$emit('input', value);

      if (this.dataCanAdd && value.length > 0) {
        const lastValue = value[value.length - 1];
        if (lastValue === this.newItem) {
          this.newItems.push(this.toObj(lastValue, lastValue));
        }
      }
    }
  },

  beforeMount: function() {
    this.checkCanAdd();
  },

  methods: {
    autocompleteSearch: function(value) {
      value = (value || '').trim();

      const hasValue = this.dataItems.find(v => v[this.itemValue] === value);

      if (hasValue) {
        this.newItem = '';
      } else {
        this.newItem = value;
      }
    },
    toObj: function(value, text) {
      const obj = {};
      obj[this.itemValue] = value;
      obj[this.itemText] = text;
      return obj;
    },
    checkCanAdd: function() {
      if (this.itemsIsObjs && this.dataCanAdd) {
        console.warn(
          'AutoComplete: desativando opção de add... component nao suporta operação de add com OBJ'
        );
        this.dataCanAdd = false;
      }
    }
  }
};
</script>

<style scoped></style>

/**
 * Alert generico
 * @typedef {Object} Alert
 * @property {number} id
 * @property {string} message
 * @property {string} type success,error
 */

/**
 * Objecto state
 * @typedef {Object} StateAlert
 * @property {Alert[]} alerts
 * @property {number} nextId
 */

/**
 * @type {StateAlert}
 */
const state = {
  alerts: [],
  nextId: 1
};

// getters
const getters = {
  /**
   * @param {StateAlert} state
   */
  errorMessages(state) {
    return state.alerts.filter(a => a.type === 'error');
  },
  /**
   * @param {StateAlert} state
   */
  successMessages(state) {
    return state.alerts.filter(a => a.type === 'success');
  }
};

// actions
const actions = {};

// mutations
const mutations = {
  /**
   * @param {StateAlert} state
   * @param {(string\|string[])} message
   */
  addErrorMessage(state, message) {
    state.alerts = [];
    if (Array.isArray(message)) {
      message.forEach(m => {
        state.alerts.push({
          id: state.nextId++,
          message: m,
          type: 'error'
        });
      });
    } else {
      state.alerts.push({
        id: state.nextId++,
        message: message,
        type: 'error'
      });
    }
  },

  /**
   * @param {StateAlert} state
   * @param {(string\|string[])} message
   */
  addSuccessMessage(state, message) {
    state.alerts = [];
    if (Array.isArray(message)) {
      message.forEach(m => {
        state.alerts.push({
          id: state.nextId++,
          message: m,
          type: 'success'
        });
      });
    } else {
      state.alerts.push({
        id: state.nextId++,
        message: message,
        type: 'success'
      });
    }
  },
  /**
   * @param {StateAlert} state
   * @param {number} id
   */
  removeMessageById(state, id) {
    const index = state.alerts.findIndex(a => a.id === id);
    if (index >= 0) {
      state.alerts.splice(index, 1);
    }
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};

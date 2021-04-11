import userApi from '../../api/user';

class User {
  constructor(userData) {
    this.roles = [];
    this.username = '';
    this.isLogged = false;
    this.token = null;

    if (userData) {
      let userDataClone = JSON.parse(JSON.stringify(userData));
      Object.assign(this, userDataClone);
      this.isLogged = true;
    }
  }

  hasRole(role) {
    // return true;
    return this.roles.indexOf(role) >= 0;
  }
}

const state = {
  loggedUser: new User()
};

// getters
const getters = {};

// actions
const actions = {
  async loadUser({ commit }, token) {
    localStorage.removeItem('ACCESS_TOKEN');

    const userMe = await userApi.login(token);
    localStorage.setItem('ACCESS_TOKEN', token);

    commit('setLoggedUser', userMe);
  },
  async loadLocalUser({ commit }) {
    console.log('loadLocalUser');
    const token = localStorage.getItem('ACCESS_TOKEN');
    if (!token) {
      commit('setLoggedUser', null);
      return;
    }

    actions.loadUser({ commit }, token);
  },
  async logoutUser({ commit }) {
    localStorage.removeItem('ACCESS_TOKEN');
    commit('setLoggedUser', null);
  }
};

// mutations
const mutations = {
  setLoggedUser(state, user) {
    state.loggedUser = new User(user);
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};

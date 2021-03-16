import userApi from '../../api/user';

class User {
  constructor(userData) {
    this.roles = [];
    this.username = '';
    this.isLogged = false;
    this.token = null

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
  async loadUser({ commit }, currentUser) {

    commit('setLoggedUser', null);

    let userResponse = null;

    userResponse = await userApi.login(
      currentUser.username,
      currentUser.password
    )

    commit('setLoggedUser', userResponse);
  },
  async logoutUser({ commit }) {
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

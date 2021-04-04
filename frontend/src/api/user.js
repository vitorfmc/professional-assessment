import axios from 'axios';

class UserMeResponse {
  constructor(userData, token) {
    this.id = userData.id;
    this.name = userData.name;
    this.username = userData.username;
    this.roles = userData.roles.map(role => role.name);
    this.imageUrl = userData.imageUrl;
    this.token = token;
  }

  hasRole(role) {
    // return true;
    return this.roles.indexOf(role) >= 0;
  }
}

export default {
  /**
   * @returns {Promise<UserMeResponse>}
   */
  async login(token) {
    try {
      let response = await axios.get(
        `http://localhost:8080/api/v1/user/me`, // TODO tirar url daqui
        {
          timeout: process.env.VUE_APP_LOGIN_TIMEOUT,
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      return new UserMeResponse(response.data, token);
    } catch (error) {
      throw error;
    }
  }
};

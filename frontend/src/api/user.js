import axios from 'axios';

export default {
  /**
   * @returns {Promise<User>}
   */
  async login(loginS, passwordS) {
    try {
      /*let response = await axios.post(
        process.env.VUE_APP_LOGIN_URL, 
        {
          username: loginS,
          password: passwordS
        },
        {
          timeout: process.env.VUE_APP_LOGIN_TIMEOUT,
          headers: {
            'Access-Control-Allow-Origin': '*'
          }
        }
      );*/

      return {
        login: 'x',//response.data.username,
        roles: [],//response.data.roles,
        token: 'x'//response.data.token
      };
      
    } catch (error) {
      throw error;
    }
  }
};

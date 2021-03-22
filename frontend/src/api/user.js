import axios from 'axios';

export default {
  /**
   * @returns {Promise<User>}
   */
  async login(loginS, passwordS) {
    try {
      let response = await axios.post(
        process.env.VUE_APP_LOGIN_URL, 
        JSON.stringify({
            grant_type: 'client_credentials'
        }),
        {
          timeout: process.env.VUE_APP_LOGIN_TIMEOUT,
          headers: {
            'Access-Control-Allow-Origin': '*',
            'content-type': 'application/x-www-form-urlencoded;charset=utf-8'
          },
          auth: {
            username: loginS,
            password: passwordS
          }
        }
      );

      return {
        login: loginS,
        roles: [response.data.scope],
        token: response.data.access_token
      };
      
    } catch (error) {
      throw error;
    }
  }
};

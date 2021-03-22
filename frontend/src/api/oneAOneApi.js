import axios from 'axios';

 export default {

  /*async save(game, user){
    try {

      let response = await axios.post(
        `${process.env.VUE_APP_GAME_URL}`, game,
        {
          timeout: process.env.VUE_APP_GAME_TIMEOUT,
          headers: {
            'Authorization': 'Bearer ' + user.token,
            'Access-Control-Allow-Origin': '*'
          }
        }
      );

      return response.data;

    } catch (error) {
      console.error('Erro ao tentar salver o jogo', error);
      throw error;
    }
  },

  async update(game, user){
    try {

      let response = await axios.put(
        `${process.env.VUE_APP_GAME_URL}/${game.code}`, game,
        {
          timeout: process.env.VUE_APP_GAME_TIMEOUT,
          headers: {
            'Authorization': 'Bearer ' + user.token,
            'Access-Control-Allow-Origin': '*'
          }
        }
      );

      return response.data;

    } catch (error) {
      console.error('Erro ao tentar atualizar o jogo', error);
      throw error;
    }
  },

  async delete(code, user){
    try {
      let response = await axios.delete(
        `${process.env.VUE_APP_GAME_URL}/${code}`,
        {
          timeout: process.env.VUE_APP_GAME_TIMEOUT,
          headers: {
            'Authorization': 'Bearer ' + user.token,
            'Access-Control-Allow-Origin': '*'
          }
        }
      );

      return response;

    } catch (error) {
      console.error('Erro ao tentar remover o jogo', error);
      throw error;
    }
  },

  async getTypes(user){
    try {

      let response = await axios.get(
        `${process.env.VUE_APP_GAME_URL}/types`,
        {
          timeout: process.env.VUE_APP_GAME_TIMEOUT,
          headers: {
            'Authorization': 'Bearer ' + user.token,
            'Access-Control-Allow-Origin': '*'
          }
        }
      );

      return response.data;

    } catch (error) {
      console.error('Erro ao tentar buscar os tipos de jogos', error);
      throw error;
    }
  },

  async getCategories(user){
    try {

      let response = await axios.get(
        `${process.env.VUE_APP_GAME_URL}/categories`,
        {
          timeout: process.env.VUE_APP_GAME_TIMEOUT,
          headers: {
            'Authorization': 'Bearer ' + user.token,
            'Access-Control-Allow-Origin': '*'
          }
        }
      );

      return response.data;

    } catch (error) {
      console.error('Erro ao tentar buscar os categorias de jogos', error);
      throw error;
    }
  },

  async findOne(code, user){
    try {
      let response = await axios.get(
        `${process.env.VUE_APP_GAME_URL}/${code}`,
        {
          timeout: process.env.VUE_APP_GAME_TIMEOUT,
          headers: {
            'Authorization': 'Bearer ' + user.token,
            'Access-Control-Allow-Origin': '*'
          }
        }
      );

      return response.data;

    } catch (error) {
      console.error('Erro ao tentar buscar o jogo', error);
      throw error;
    }
  },*/

  async findAll(page, max, user, params) {
    try {

      if(params != null){
          params.limit = max;
          params.offset = page;
      }else{
        params = {
          limit: max,
          offset: page
        }
      }

      let response = await axios.get(
        `${process.env.VUE_APP_ONEAONE_URL}`,
        {
          timeout: process.env.VUE_APP_ONEAONE_TIMEOUT,
          headers: {
            'Authorization': 'Bearer ' + user.token,
            'Access-Control-Allow-Origin': '*'
          },
          params: params
        }
      );
      
      return {
        content: response.data.content,
        firstPage: response.data.firstPage,
        lastPage: response.data.lastPage,
        pageNumber: response.data.pageNumber,
        pageSize: response.data.pageSize,
        totalElements: response.data.totalElements,
        totalPages: response.data.totalPages
      };

    } catch (error) {
      console.error('Erro ao tentar buscar os oneAOnes', error);
      throw error;
    }
  },

};

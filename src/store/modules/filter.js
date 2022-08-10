/**
 * filter 정보 관리 
 */
 export default {

  namespaced : true,

  state : {
    period: -1,
    timeTaken: 4,
    ingredientIds: [],
    sort: 0,
    listView: 0,
  },

  getters : {
    period(state){
      return state.period;
    },

    timeTaken(state){
      return state.timeTaken;
    },

    ingredientIds(state){
      return state.ingredientIds;
    },

    sort(state){
      return state.sort;
    },

    listView(state){
      return state.listView;
    }
  },

  mutations : {

    setPeriod(state, period){
      state.period = period;
    },

    setTimeTaken(state, timeTaken){
      state.timeTaken = timeTaken;
    },

    setIngredientIds(state, ingredientIds){
      state.ingredientIds = ingredientIds;
    },

    setSort(state, sort){
      state.sort = sort;
    },

    setListView(state, listView){
      state.listView = listView;
    }
  },

  actions : {

    setPeriod({commit}, period){
      commit('setPeriod', period);
    },

    setTimeTaken({commit}, timeTaken){
      commit('setTimeTaken', timeTaken);
    },

    setIngredientIds({commit}, ingredientIds){
      commit('setIngredientIds', ingredientIds);
    },

    setSort({commit}, sort){
      commit('setSort', sort);
    },

    setListView({commit}, listView){
      commit('setListView', listView);
    }
  }
}
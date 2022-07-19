/**
 * filter 정보 관리 
 */
 export default {

  namespaced : true,

  state : {
    period: -1,
    timeTaken: -1,
    ingredientIds: [],
    sort: 0,
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
    }
  },

  mutations : {

    setPeriod(state, period){
      if(state.period == period){
        state.period = -1;
      }else{
        state.period = period;
      }
      // console.log(state.period);
    },

    setTimeTaken(state, timeTaken){
      if(state.timeTaken == timeTaken){
        state.timeTaken = -1;
      }else{
        state.timeTaken = timeTaken;
      }
      // console.log(state.timeTaken);
    },

    setIngredientIds(state, ingredientIds){
      state.ingredientIds = ingredientIds;
    },

    setSort(state, sort){
      state.sort = sort;
    },
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
  }
}
import Vue from 'vue'
import Vuex from 'vuex'

import auth from './modules/auth'
// import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)

export default new Vuex.Store({    
  
    state : {
        count: 0
    },

    getters : {
        count(state){
            return state.count;
        },
        formatCnt(state){
            return String(state.count).padStart(2, '0');
        }
    },

    mutations : {
        setCount(state, count){
            state.count = count;
        },
        changeCnt(state, value){
            state.count += value;
        }
    },

    actions : {
        setCount({commit}, count){
            commit('setCount', count);
        },
        // changeCnt(context, value){
        //     context.commit('changeCnt', value);
        // }
        changeCnt({commit}, count){
            commit('changeCnt', count);
            },
        },
    modules: {
        auth: auth
    },
//   plugins : [createPersistedState({
//     paths : ["user"],
//     storage : window.sessionStorage
//   })]
})

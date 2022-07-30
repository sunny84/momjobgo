import Vue from 'vue'
import Vuex from 'vuex'
// import recipe from './modules/recipe'
import user from './modules/user'
import filter from './modules/filter'
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    // recipe
    user: user,
    filter: filter,
  },
  plugins : [createPersistedState({
    paths : ["user"],
    storage : window.sessionStorage
  })]
})

import Vue from 'vue'
import Vuex from 'vuex'
import page from './modules/page'
import calculate from './modules/calculate'
import count from './modules/count'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {   
  },
  modules: {
    page,
    calculate,
    count
  }
})

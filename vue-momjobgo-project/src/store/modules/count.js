export default {
    // module
    namespaced : true,
    
    state: {
      count: 0,
    },
    getters: {
      formatCnt(state) {
        return String(state.count).padStart(2, '0');
      },
    },
    mutations: {
      increment(state, value) {
        state.count += value;
        // console.log(state.count)
      },
      decrease(state, value) {
          state.count -= value;
        //   console.log(state.count)
      }
    },
    actions: {   
        increment(context, value) {
            context.commit('increment', value); 
        }
    }
  }
  
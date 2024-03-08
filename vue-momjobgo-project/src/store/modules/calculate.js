export default {
    // module
    namespaced : true,
    // data
    state: () => ({
        num1: 0,
        num2: 0,
        calculate: '{산술연산자}'        
    }),
    // computed
    getters : {
        addition(state) {
          state.calculate = "+"
          return parseFloat(state.num1) + parseFloat(state.num2);
        },
        subtraction(state) {
          state.calculate = "-"
          return parseFloat(state.num1) - parseFloat(state.num2);
        },
        multiplication(state) {
          state.calculate = "*"
          return parseFloat(state.num1) * parseFloat(state.num2);
        },
        division(state) {
          state.calculate = "/"
          return parseFloat(state.num1) / parseFloat(state.num2);
        }
    },
    // methods
    // 변이, 변경
    mutations : {
        updateState(state, payload) {
            // ['num1', 'num2', 'calculate']
            Object.keys(payload).forEach(key => {
                state[key] = payload[key]
            })
        },
        setNum1(state, value) {
          state.num1 = value;
        },
        setNum2(state, value) {
          state.num2 = value;
        }
    },
    // 비동기
    actions : {
        test({commit}, payload) {
            const {num1, num2, calculate} = payload
            context.commit('updateState', {
                num1: num1,
                num2: num2,
                calculate: calculate
            })
        },
        setNum1({commit}, value) {
          commit('setNum1', value);
        },
        setNum2({commit}, value) {
          commit('setNum2', value);
        }
    }
}
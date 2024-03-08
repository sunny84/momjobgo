export default {

    namespaced : true,

    state : {
        name : '방문자'
    },

    getters : {
        name(state){
            return state.name;
        }
    },

    mutations : {
        setName(state, name){
            state.name = name;
        }
    },

    actions : {
        setName({commit}, name){
            commit('setName', name);
        }
    }
}
export default {
    namespaced: true,
  
    state : {
        name: '',
        nameList: [], 
    },

    getters : {
        name(state){
            return state.name;
        },
        nameList(state){
            return state.nameList;
        },
    },

    mutations : {
        setName(state, value){
            state.name = value;
        },
        setNameList(state, nameList){
            state.nameList = nameList;
        },
    },

    actions : {
        setName({commit}, value){
            commit('setName', value);
        },
        setNameList({commit}, value){
            commit('setNameList', value);
        },
    },
}
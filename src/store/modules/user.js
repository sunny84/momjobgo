/**
 * 유저정보와 토큰을 set하고 
 * 토큰여부를 확인하는 내용을 작성
 */
export default {

    namespaced : true,

    state : {
        token : sessionStorage.getItem('access-token'),
        user : {
            snsId : ''
        }
    },

    getters : {
        snsId(state){
            return state.user.snsId;
        },

        token(state){
            return state.token;
        },

        hasToken(state){
            return !!state.token;
        }
    },

    mutations : {

        setToken(state, token){
            state.token = token;
            sessionStorage.setItem('access-token', token);
        },

        setsnsId(state, snsId){
            state.user.snsId = snsId;
        },
    },

    actions : {

        setsnsId({commit}, snsId){
            commit('setsnsId', snsId);
        },
        
        setToken({commit}, token){
            console.log("token", token);
            commit('setToken', token);
        }
    }
}
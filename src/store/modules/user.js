/**
 * 유저정보와 토큰을 set하고 
 * 토큰여부를 확인하는 내용을 작성
 * 카카오 닉네임과 프로필사진 set
 */
export default {

    namespaced : true,

    state : {
        token : '',
        user : {
            snsId : ''
        },
        user : {
            nickname : ''
        },
        user : {
            profile_img : ''
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
        },

        nickname(state){
            return state.user.nickname;
        },

        profile_img(state){
            return state.user.profile_img;
        }
    },

    mutations : {

        setToken(state, token){
            state.token = token;
        },

        setSnsId(state, snsId){
            state.user.snsId = snsId;
        },

        setNickname(state, nickname){
            state.user.nickname = nickname;
        },

        setProfile_img(state, profile_img){
            state.user.profile_img = profile_img;
        },
    },

    actions : {

        setSnsId({commit}, snsId){
            commit('setSnsId', snsId);
        },
        
        setToken({commit}, token){
            commit('setToken', token);
        },

        setNickname({commit}, nickname){
            commit('setNickname', nickname);
        },

        setProfile_img({commit}, profile_img){
            commit('setProfile_img', profile_img);
        }
    }
}
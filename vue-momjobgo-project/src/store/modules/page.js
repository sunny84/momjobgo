import HomeView from '@/views/HomeView.vue'
import AboutView from '@/views/AboutView.vue'
import CountView from '@/views/CountView.vue'
import CalculateView from '@/views/CalculateView.vue'
import AxiosView from '@/views/AxiosView.vue';
import SignUpView from '@/views/SignUpView.vue';
import LoginView from '@/views/LoginView.vue';
import DeleteView from '@/views/DeleteView.vue';

export default {

    namespaced : true,

    state : {

        title : '',

        basePath : '',

        list : {
            home : {
                path: `/home`,
                name: `home`,
                meta: {
                    title: `Home`,
                    icon: `home`
                },
                component : HomeView
            },

            count : {
                path: `/count`,
                name: `count`,
                meta: {
                    title: `Count`,
                    icon: `event`
                },
                component : CountView
            },

            calculate : {
                path: `/calculate`,
                name: `calculate`,
                meta: {
                    title: `Calculate`,
                    icon: `event`
                },
                component : CalculateView
            },

            about : {
                path: `/about`,
                name: `about`,
                meta: {
                    title: `About`,
                    icon: `info`
                },
                component : AboutView
            }, 

            axios: {
                path: `/axios`,
                name: `axios`,
                meta: {
                    title: `Axios`,
                    icon: `search`,
                },
                component : AxiosView,
            },

            signup: {
                path: `/signup`,
                name: `signup`,
                meta: {
                    title: `Signup`,
                    icon: `edit`,
                },
                component : SignUpView,
            },

            login: {
                path: `/login`,
                name: `login`,
                meta: {
                    title: `Login`,
                    icon: `login`,
                },
                component : LoginView,
            },

            delete: {
                path: `/delete`,
                name: `delete`,
                meta: {
                    title: `Delete`,
                    icon: `delete`,
                },
                component : DeleteView,
            },
        },

        visible : {
            header : true,
            navi : true,
            footer : true
        }

    },

    getters : {
        title(state){
            return state.title;
        },

        basePath(state){
            return state.basePath;
        },

        menuList(state){
            return state.list;
        },

        visible(state){
            return state.visible;
        }
    },

    mutations : {
        setTitle(state, title){
            state.title = title;
        },

        setVisible(state, {key, value}){
            state[key] = value;
        }
    },

    actions : {
        setTitle({commit}, title){
            commit('setTitle', title);
        },

        setVisible({commit}, payload){
            commit('setVisible', payload); 
        }
    }

}
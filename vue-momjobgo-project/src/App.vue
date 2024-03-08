<template>
    <v-app id="app">
        
        <NavView></NavView>
        
        <HeaderView></HeaderView>

        <MainView></MainView>

        <FooterView></FooterView>
        
    </v-app>
</template>

<script>
import NavView from "./views/layout/NavView.vue";
import HeaderView from "./views/layout/HeaderView.vue";
import FooterView from "./views/layout/FooterView.vue";
import MainView from "./views/layout/MainView.vue";
import { mapGetters } from "vuex";

export default {
    name: "App",

    data: () => ({}),

    components: {
        NavView,
        HeaderView,
        MainView,
        FooterView,
    },

    computed : {
        ...mapGetters('page',['menuList'])
    },

    created() {
        if(window.location.pathname === '/'){
            this.$router.push(this.menuList.home.path)
        }
        if(window.location.pathname === '/count'){
            this.$router.push(this.menuList.count.path)
        }
        if(window.location.pathname === '/calculate'){
            this.$router.push(this.menuList.calculate.path)
        }
        if(window.location.pathname === '/info'){
            this.$router.push(this.menuList.info.path)
        }
        if(window.location.pathname === '/about'){
            this.$router.push(this.menuList.about.path)
        }
        if(window.location.pathname === '/search'){
            this.$router.push(this.menuList.search.path)
        }
        if(window.location.pathname === '/signup'){
            this.$router.push(this.menuList.signup.path)
        }
        if(window.location.pathname === '/login'){
            this.$router.push(this.menuList.login.path)
        }
        if(window.location.pathname === '/delete'){
            this.$router.push(this.menuList.delete.path)
        }
        else{
            alert("page no");
        }
    }

};
</script>

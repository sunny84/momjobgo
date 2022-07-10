<template>
    <v-app>
        <v-card width="500" style="top: 25%" class="mx-auto mt-5">
            <v-card-title class="pb-0">
                <h3>Login</h3>
            </v-card-title>

            <v-card-text>
                <v-form>
                    <v-text-field label="Id" v-model="id" @keyup.enter="$refs.pwd.focus()"/>
                    <v-text-field
                    ref="pwd"
                    :type="showPassword ? 'text' : 'password'"
                    label="Password"
                    :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                    @click:append="showPassword = !showPassword"
                    v-model="password"
                    @keyup.enter="login"
                    />
                </v-form>
            </v-card-text>

            <v-divider></v-divider>

            <v-card-actions>
                <SignUpModalViewVue btn-color="success">
                    <template v-slot:title>@회원가입@</template>
                </SignUpModalViewVue>
                <v-btn color="info" @click="login" style="margin-left: 8px;">로그인</v-btn>
            </v-card-actions>

        </v-card>
    </v-app>
</template>

<script>
/**LoginView.vue */
    import SignUpModalViewVue from "./SignUpModalView.vue";
    // import axios from "axios";
    import axios from '@/plugins/axios';
    import store from '@/store';
    import Code from '@/static/Code';
    import BoardView from '@/views/board/BoardsView.vue';

    /** mapActions,mapGetters 라는 helper를 이용하여 객체의 형태로 바인딩함  */
    import { mapActions, mapGetters } from "vuex"  

    export default {


        data : () => ({
            showPassword: false,
            id: "",
            password: "",
        }),

        mounted() {
            this.setAllVisible(false);
        },

        beforeDestroy() {
            this.setAllVisible(true);
        },

        methods : {
            ...mapActions('page', ['setAllVisible']), /**pages.js의  */
            ...mapActions('user', ['setToken','setName','setId']),

            login() {

                console.log(this.id)
                console.log(this.password);                
                console.log("------------start----------------")
                var url = 'http://localhost:8090/auth/user';
                var data = {id: this.id, pwd: this.password}
                axios.post(url,data)
                    .then(function(response){
                        console.log(response);
                        
                        // const token = response.data.token;
                        // console.log(token);
                        // location.href = this.basePath; 

                    if(response?.status === this.HTTP_OK){
                    const token = response.data.token;
                    this.setToken(token);
                    // location.href = this.basePath;  

                }    


                    }).catch(function(error){
                        console.log(error)
                    });
                // 로그인을 구현하세요.
                // async login() {
                // const response = await this.axios(`/auth/user`,"post", {
                //     id: this.id,
                //     pwd: this.password
                // });



            }
        },

        components : {
            SignUpModalViewVue
        }

    }
</script>

<style>

</style>
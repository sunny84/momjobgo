<template>
    <v-dialog v-model="dialog" persistent max-width="600px">
        <template v-slot:activator="{ on, attrs }">
            <v-btn :color="btnColor" light v-bind="attrs" v-on="on"><slot name="title"></slot></v-btn>
        </template>
        <v-card>
            <v-card-title>
                <span class="text-h5">회원 가입</span>
            </v-card-title>
                <v-card-text>
                    <v-container>
                    <v-row>
                        <v-col cols="12">
                            <v-text-field label="아이디*" required v-model="user.id"></v-text-field>
                        </v-col>
                        <v-col cols="12">
                            <v-text-field label="비밀번호*" type="password" required v-model="user.pwd"></v-text-field>
                        </v-col>
                        <v-col cols="12">
                            <v-text-field ref="checkPwd" label="비밀번호 확인*" type="password" required v-model="user.checkPwd"></v-text-field>
                        </v-col>
                        <v-col cols="12">
                            <v-text-field label="이름*" required v-model="user.name"></v-text-field>
                        </v-col>
                    </v-row>
                    </v-container>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="dialog = false"> 취소 </v-btn>
                    <v-btn color="blue darken-2" text @click="submit"> @가입@ </v-btn>
                </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
/** SignUpModalView.vue */
import axios from "axios";

    export default { 
        props : { /* 단방향 바인딩 */
            btnColor : {
                type : String,
                default : 'primary'
            }
        },

        data: () => ({
            dialog: false,

            user : {
                id : '',
                pwd : '',
                checkPwd : '',
                name : ''
            }
        }),

        methods: {
            submit() {
                console.log(this.user.id)
                console.log(this.user.pwd); 
                // console.log(this.user.checkPwd);   
                console.log(this.user.name);
                // console.log(this.data);                    
                console.log("-----------SignUp-start----------------")
                var url = 'http://localhost:8090/auth/user/new'; //pwd: 비밀번호는 영어나 숫자 6~12자리 이내로 입력
                var data = {id: this.user.id, pwd: this.user.pwd, name: this.user.name}
                axios.post(url,data)
                    .then(function(response){
                        console.log(response);
                    }).catch(function(error){
                        console.log(error)
                    });
                // 회원가입을 구현하세요.
            }
        },

    }
</script>

<style scoped>

</style>
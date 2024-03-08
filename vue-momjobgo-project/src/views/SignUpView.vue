<template>
    <div>
        <p>회원가입</p>
        <p>ID : <input type="text" v-model="id"></p>
        <p>PW : <input type="password" v-model="pwd"></p>        
        <p>이름 : <input type="text" v-model="name"></p>
        <button @click="signUp">가입</button>
    </div>
</template>

<script>
import axios from "axios"

export default {
    data : () => ({
        id : '',
        pwd: '',
        name: ''
    }),

    methods : {
        signUp() {
            axios.post('https://api.devcury.kr/auth/user/new', {
                id : this.id,
                pwd : this.pwd,
                name : this.name
            }).then(response => {
                if(response.status === 201){
                    alert('회원가입 성공');
                }
            }).catch(error=>{
                alert(error.response.data.error);
            })
        }
    }
}
</script>

<style>
p {
    margin-top: 20px;
}
input {
    width: 100px;
    overflow: hidden;
    border: 1px solid rgb(37, 124, 224);
    border-radius: 5px;  
}
button {
    width: 180px;
    color: #fff;
    background-color: rgb(37, 124, 224);
    border-radius: 5px;
}
</style>
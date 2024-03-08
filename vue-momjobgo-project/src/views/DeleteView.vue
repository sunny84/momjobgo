<template>
    <div>
        유저삭제
        <p>
            토큰 : <input type="text" v-model="token">
        </p>
        <button @click="deleteUser">삭제</button>
    </div>
</template>

<script>
import axios from "axios"

export default {
    data: () => ({
        token : ''
    }),

    methods : {
        deleteUser() {
            if(!confirm('정말로 삭제 하시겠습니까?')){
                return false;
            }

            axios.delete('https://api.devcury.kr/api/auth/user', {
                headers : {
                    Authorization: `Bearer ${this.token}`
                }
            }).then(response => {
                if(response.status === 200){
                    alert('삭제되었습니다.');
                }
            }).catch(error=> {
                alert(error.response.data.error);
            });
        }
    }
}
</script>

<style scoped>

input {
    width: 800px;
}
</style>
<template>
  <div>
    <h1>This is Axios utilization page.</h1>   
    <div class="wrap">
        <select v-model="type">
            <option value="1">웹문서</option>
            <option value="2">이미지</option>
            <option value="3">책검색</option>
        </select>
        <input type="text" v-model="search" @keyup.enter="callData">
        <button @click="callData">검색</button>
        
        <div v-if="type == 1 || type == 3">
            <table>
                <tr>
                    <th>제목</th>
                    <th>내용</th>
                </tr>
                <tr v-for="(item, index) in list" :key="index">
                    <td>
                        <a :href="item.url" target='_blank'>
                            <span v-html="item.title"></span>
                        </a>
                    </td>
                    <td>
                        <span v-html="item.contents"></span>
                    </td>
                </tr>
            </table>
        </div>

        <div v-else-if="type == 2">
            <table>
                <tr>
                    <th>제목</th>
                    <th>이미지</th>
                </tr>
                <tr v-for="(item, index) in list" :key="index">
                    <td>
                        <a :href="item.doc_url" target='_blank'>
                            {{item.display_sitename}}
                        </a>
                    </td>
                    <td>
                        <img :src="item.thumbnail_url">
                    </td>
                </tr>
            </table>
        </div>

    </div>
  </div>
</template>

<script>
import axios from "axios"

export default {
    data : ()=> ({
        type : '1',
        search : '',
        list : []
    }),

    methods: {
        callData() {
            switch(this.type){
                case '1' :
                    this.callWebDoc();
                    break;
                case '2' : 
                    this.callImg();
                    break;
                case '3' : 
                    this.callBook();
                    break;
            }
        },

        callWebDoc() {
            axios.get(`https://dapi.kakao.com/v2/search/web?query=${this.search}&page=1&size=10&sort=recency`,{
                headers: {
                    Authorization: `KakaoAK ${process.env.VUE_APP_KAKAO_KEY}`
                }
            }).then(response=>{
                this.list = response.data.documents;
            }).catch(error=>{
                console.error(error);
            });
        },

        callImg() {
            axios.get(`https://dapi.kakao.com/v2/search/image?query=${this.search}&page=1&size=10&sort=recency`,{
                headers: {
                    Authorization: `KakaoAK ${process.env.VUE_APP_KAKAO_KEY}`
                }
            }).then(response=>{
                this.list = response.data.documents;
            }).catch(error=>{
                console.error(error);
            });
        },

        callBook() {
            axios.get(`https://dapi.kakao.com/v3/search/book?query=${this.search}&page=1&size=10&sort=accuracy`,{
                headers: {
                    Authorization: `KakaoAK ${process.env.VUE_APP_KAKAO_KEY}`
                }
            }).then(response=>{
                this.list = response.data.documents;
            }).catch(error=>{
                console.error(error);
            });
        }
    }
}
</script>

<style scoped>
h1 {
    border-bottom: 1px solid #999;
}
.wrap {
  /* display: flex; */
  margin-top: 20px;
}
select{
    width: 80px;
    border: 1px solid rgb(37, 124, 224);
    border-radius: 5px;  
    text-align: center;
}
input {
    width: 200px;
    overflow: hidden;
    border: 1px solid rgb(37, 124, 224);
    border-radius: 5px;  
    margin: 5px;
    padding: 0px 10px;
    /* text-align: center; */
}
button {
    width: 60px;
    color: #fff;
    background-color: rgb(37, 124, 224);
    border-radius: 5px;  
}
table, td, th {
  border-bottom: 1px solid #999;
}

table {
    margin: 10px;
    border-collapse: collapse;
}
</style>
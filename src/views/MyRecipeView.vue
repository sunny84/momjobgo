<template>
    <!--HEADER-->
    <!--CONTENT-->
    <div class="contents">        
        <button @click="callData">초기화</button>
        <!--{{$t("content.myRecipe")}} {{ 나의 레시피 수 }}-->
        <h4>{{$t("content.myRecipe")}} #</h4>
        <button @click="callWrite">{{$t("button.write")}}</button>
        <!--
            레시피 목록 기본 노출 3개
            <a herf="레시피 상세페이지로 이동">{{img}}{{title}}{{sub_title}}{{time}}{{start}}{{?}}</a>
            <button>레시피 박스 저장</button>
        -->
        <tr v-for="(item, index) in list" :key="index">
          <td>
            <img :src="item.thumbnail_url"/>
          </td>
          <td>
            <a :href="item.name" target="_blank">
              {{item.name}}{{item.index}}
            </a>
          </td>
        </tr>
    </div>
    <!--FOOTER-->
</template>

<script>
import axios from "axios"

export default {
    name : "MyRecipeView",
    data: ()=>({
      list : []
    }),
    methods : {
      callData() {
        console.log("callData");
        this.callRecipeBox();
      },
      callWrite() {
        console.log("callWrite");
        axios.get(`https://local.devcury.kr/recipebox/get/all`, {
            // headers: {
            //   "User-Agent": "test",
            // },
            // httpsAgent: new https.Agent({
            //   rejectUnauthorized: false, //허가되지 않은 인증을 reject하지 않겠다!
            // })
        }).then(response=>{
          this.list = response.data;
        }).catch(error=>{
          console.error(error);
        })
      },
      async callRecipeBox() {
          axios.get(`http://localhost:8090/recipebox/get/all`, {
          }).then(response=>{
            this.list = response.data;
            console.log(response.data);
          }).catch(error=>{
            console.error(error);
          })
      }

    },
}
</script>
<style>
</style>

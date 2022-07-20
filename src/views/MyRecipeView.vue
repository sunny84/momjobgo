<template>
    <!--HEADER-->
    <!--CONTENT-->
    <div class="contents">        
        <h4>{{$t("content.myRecipe")}} {{ list.length }}</h4>
        <button @click="callWrite">{{$t("button.write")}}</button>
        <!-- <confirm-input 
            :text="$t('button.addNewBox')"
            :title="$t('button.addNewBox')"
            :value="boxName"
            :callback="text => updateRecipeBox(text)"
        /> -->
        <tr v-for="(item, index) in list" :key="index">
          <td>
            <router-link :to="'/recipe/'+item.recipeId"><!-- TODO: 경로 확인 -->
              <p>
                <img :src=item.img width="200px" height="150px" @error="setEmptyImg">
              </p>
            </router-link>
              {{item.title}} <button @click="callRecipeBox(item.recipeId)">ㅁ</button><br/>
              {{item.subTitle}}<br/>
              {{item.timeTaken}} ★{{item.score}}.0 ... ({{item.commentsNumber}})<br/>
          </td>
          <td>
          </td>
        </tr>
    </div>
    <!--FOOTER-->
</template>

<script>
import axios from "axios"
import emptyImg from '@/assets/emptyImg.png'
import { mapGetters } from "vuex"
import ConfirmInput from 'vue-confirm-input'

export default {
    name : "MyRecipeView",
    data: ()=>({
      list : [],
      recipe : [],
      contents : [],
      score : [],
      timeTaken : [],
      writer : 1,
      mainPicture : '',
      defaultRecipeBox : [],
    }),
    components: {
        ConfirmInput
    },
    computed : {  
      ...mapGetters('user', ['hasToken', 'token'])
    },
    created() {
      this.initialize();
    },
    methods : {
      initialize() {
        this.getDefaultBoxId();
        this.callContents();
      },
      async getDefaultBoxId() {
        const response = await this.$api(
        `http://localhost:8090/api/recipebox/default`,
        "get"
        );
        if (response.status === this.HTTP_OK || response.status === this.HTTP_CREATED) {
            this.defaultRecipeBox = response.data;
        }
        if(response.data.isNaN){
          // create Default Box
          const response = await this.$api(
          `http://localhost:8090/api/recipebox/default`,
          "post",
          {}
          );
          if (response.status === this.HTTP_OK || response.status === this.HTTP_CREATED) {
              this.defaultRecipeBox = response.data;
          }
        }
      },
      async callContents(recipeId) {
        const response = await this.$api(
        `http://localhost:8090/api/contents/`,
        "get"
        );
        if (response.status === this.HTTP_OK) {
            this.contents = response.data;
        }
        if(this.contents.length > 0)
        {
          var i = 0;
          this.list = [];
          while(i < this.contents.length ){
            // console.log(i,"회");
            const response = await this.$api(
            `http://localhost:8090/Recipe/contents=${this.contents[i].id}`,
            "get"
            );
            if (response.status === this.HTTP_OK) {
                this.recipe = response.data;
            }
            const response2 = await this.$api(
            `http://localhost:8090/score/recipe/${this.recipe[0].id}`,
            "get"
            );
            if (response2.status === this.HTTP_OK) {
                this.score[i] = response2.data.score;
                console.log(response2.data.score);
            }
            const response3 = await this.$api(
            `http://localhost:8090/time-taken/${this.recipe[0].timeTakenId}`,
            "get"
            );
            if (response3.status === this.HTTP_OK) {
                this.timeTaken[i] = response3.data.time;
            }

            this.list.push({
              title: this.contents[i].title,
              subTitle: this.contents[i].subTitle,
              score: this.score[i],
              timeTaken: this.timeTaken[i],
              recipeId: this.recipe[0].id,
              img: 'http://localhost:8090/file/download?fileId=' + this.contents[i].id,
              // img: 'http://localhost:8090/file/download/contents?contentsId=' + this.contents[i].id,
              commentsNumber : 66   // TODO: comments
            });

            i = i + 1;          
          }
          // console.log(`list: ${this.list}`);
        } 
      },
      async callRecipeBox(recipeId) {
        console.log("recipebox save button")
        //로그인 확인
        const _this = this;
        if( !_this.hasToken ){
          console.log("token is alive")
          console.log("token: "+_this.token)
          // 로그인 페이지로 이동
          location.href='/login';
        }
        // 기본박스에 저장
        const response = await this.$api(
        `http://localhost:8090/api/reciperecipebox?box=${this.defaultRecipeBox.id}&recipe=${recipeId}`,
        "post"
        );
        if (response.status === this.HTTP_OK) {
          console.log(response.data);
        }
        // 레시피박스 페이지로 이동
        location.href=`/recipebox/${this.defaultRecipeBox.id}`;
      },
      callWrite() {
        console.log("write button");
        location.href=`/write`;
      },
      setEmptyImg(e) {
        e.target.src=emptyImg;
      },
      async updateRecipeBox(name) {
        console.log("updateRecipeBox : "+name);
        const response = await this.$api(
        `http://localhost:8090/api/recipebox/${this.defaultRecipeBox.id}`,
        "patch",
        {name: name}
        );
        if (response.status === this.HTTP_CREATED) {
          console.log("이름 변경 성공")
        }
      }
    },
}
</script>
<style scoped>
ul{
 list-style:none;
 padding-left:0px;
}
a:link{color:black;text-decoration:none;}
a:visited{color:gray;text-decoration:none;}
a:hover{color:orange;text-decoration:none;}
</style>

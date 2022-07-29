<template>
    <!--HEADER-->
    <!--CONTENT-->
    <div class="contents">        
        <h4>{{$t("content.myRecipe")}} {{ recipeList.length }}</h4>
        <button @click="callWrite">{{$t("button.write")}}</button>
        <!-- <confirm-input 
            :text="$t('button.addNewBox')"
            :title="$t('button.addNewBox')"
            :value="boxName"
            :callback="text => updateRecipeBox(text)"
        /> -->   
        <ul v-for="(recipe, index) in recipeList" :key="index">
          <li v-if="recipe.cookingOrderExist == 'N'">
            <router-link :to="'/recipedetail/'+recipe.recipeId">
            <p>n/m</p>
            <img 
              :src="recipe.mainImg?`${$API_SERVER}/file/download/thumbnail?fileId=${recipe.mainImg}`:mainPicture"
              width="200px" 
              height="150px" 
              @error="setEmptyImg"
            />
            </router-link>
          </li>
          <li v-if="recipe.cookingOrderExist == 'Y'">
            <hooper :auto-play="true" :play-speed="3000">
              <slide v-for="(order, idx) in recipe.cookingOrder" :key="idx">
                <router-link :to="'/recipedetail/'+recipe.recipeId">
                  <p>{{ order.contentsNo }}/{{ recipe.cookingOrder.length }}</p>
                  <img 
                    :src="order.imgId?`${$API_SERVER}/file/download/thumbnail?fileId=${order.imgId}`:mainPicture" 
                    width="200px" 
                    height="150px" 
                    @error="setEmptyImg"
                  />
                </router-link>
              </slide>
              <!-- <hooper-navigation slot="hooper-addons"></hooper-navigation> -->
            </hooper> 
          </li>
          <li>
            {{recipe.title}} <button @click="callRecipeBox(recipe.recipeId)">ㅁ</button><br/>
            {{recipe.subTitle}}<br/>
            {{recipe.timeTaken}} ★{{recipe.score}}.0 ... (66)<br/><!-- TODO: comments.length -->
          </li>
        </ul>
    </div>
    <!--FOOTER-->
</template>

<script>
import emptyImg from '@/assets/emptyImg.png'
import { mapGetters } from "vuex"
import ConfirmInput from 'vue-confirm-input'
import { Hooper, Slide, Navigation as HooperNavigation } from "hooper";

export default {
    name : "MyRecipeView",
    data: ()=>({
      recipeList : [],
      mainPicture : '',
      defaultRecipeBox : [],
    }),
    components: {
        ConfirmInput,
        Hooper,
        Slide,
        HooperNavigation,
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
        this.getMyRecipeList();
      },
      async getDefaultBoxId() {
        const response = await this.$api(
        `${this.$API_SERVER}/api/recipebox/default`,
        "get"
        );
        if (response.status === this.HTTP_OK || response.status === this.HTTP_CREATED) {
            this.defaultRecipeBox = response.data;
        }
        if(response.data.isNaN){
          // create Default Box
          const response = await this.$api(
          `${this.$API_SERVER}/api/recipebox/default`,
          "post",
          {}
          );
          if (response.status === this.HTTP_OK || response.status === this.HTTP_CREATED) {
              this.defaultRecipeBox = response.data;
          }
        }
      },
      async getMyRecipeList() {
          this.list = [];
          const response = await this.$api(
          `${this.$API_SERVER}/api/Recipe/mine`,
          "get",
          );            
          if (response.status === this.HTTP_OK) {
              response.data.forEach( obj => {
                // console.log(obj);
                this.recipeList.push(obj);
              });                 
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
        `${this.$API_SERVER}/api/reciperecipebox?box=${this.defaultRecipeBox.id}&recipe=${recipeId}`,
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
        `${this.$API_SERVER}/api/recipebox/${this.defaultRecipeBox.id}`,
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
@import "@/assets/carousel.css";
@import "@/assets/navigation.css";
@import "@/assets/pagination.css";
@import "@/assets/progress.css";
@import "@/assets/slide.css";
ul{
 list-style:none;
 padding-left:0px;
}
a:link{color:black;text-decoration:none;}
a:visited{color:gray;text-decoration:none;}
a:hover{color:orange;text-decoration:none;}
</style>

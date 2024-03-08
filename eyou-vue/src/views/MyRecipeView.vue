<template>
  <div>
    <main class="recipebox">
    <!--HEADER-->
    <BoxListMenu :key="recipeId"></BoxListMenu>
    <!--CONTENT-->
    <div>
        <div class="wrap_allnum fl">
            <span class="dp-inline-block fl">{{$t("content.myRecipe")}} </span>
            <span class="num">{{ recipeList?recipeList.length:0 }}</span>
        </div>
        <div class="btn btn-default fr margin-bottom-20">
          <span class="padding-right-5" @click="callWrite">{{$t("button.write")}}</span>
        </div>
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
    </main>
    </div>
    <!--FOOTER-->
</template>

<script>
import emptyImg from '@/assets/emptyImg.png'
import { mapGetters } from "vuex"
import ConfirmInput from 'vue-confirm-input'
import { Hooper, Slide, Navigation as HooperNavigation } from "hooper";
import BoxListMenu from "../components/BoxListMenu.vue";

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
    BoxListMenu
},
    computed : {  
      ...mapGetters('box', ['recipeId'])
    },
    created() {
      this.initialize();
    },
    methods : {
      initialize() {
        this.getDefaultBoxId();
        this.createDefaultBox();
        this.getMyRecipeList();
      },
      async getDefaultBoxId() {
          await this.$api(`${this.$API_SERVER}/api/recipebox/default`, "get").then(
            (res) => {
              if (res.status === this.HTTP_OK || res.status === this.HTTP_CREATED) {
                  this.defaultRecipeBox = res.data;            
              } else {
                console.log("NOT Ok", res.status);
              }
            }
          );
      },
      async createDefaultBox() {
        if(this.defaultRecipeBox.isNaN){
            await this.$api(`${this.$API_SERVER}/api/recipebox/default`, "post", {}).then(
            (res) => {
                if (res.status === this.HTTP_OK || res.status === this.HTTP_CREATED) {
                    this.defaultRecipeBox = res.data;
                } else {
                  console.log("NOT Ok", res.status);
                }
            });
        }
      },
      async getMyRecipeList() {
        await this.$api(`${this.$API_SERVER}/api/Recipe/mine`, "get").then(
          (res) => {
            if (res.status === this.HTTP_OK) {
                res.data.forEach( obj => {
                  // console.log(obj);
                  this.recipeList.push(obj);
                });                 
            } else {
              console.log("NOT Ok", res.status);
            }
          }
        );
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

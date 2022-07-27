<template>
  <div>
    <!--HEADER-->
    <h4>{{ $t("title.recipeBox") }}</h4>
    <div class="menu">
        <ul>
            <li><router-link :to="'/myrecipe'">{{ $t("menu.myRecipe") }}</router-link></li>
            <li><router-link :to="'/recipeboxlist'">{{ $t("menu.savedRecipe") }}</router-link></li>
            <li><router-link :to="'#'">{{ $t("menu.historyRecipe") }}</router-link></li>
        </ul>
    </div>
    <!--CONTENT-->
    <div class="contents">        
        <button @click="callEdit">{{$t("button.edit")}}</button>
        <tr v-for="(box, index) in recipeBoxes" :key="index">
          <td>
            <router-link :to="'/recipebox/'+box.id">
              <ul>
                <li v-if="box.recipe?box.recipe.length:0"><!-- 레시피가 있는 박스 -->
                  <ul v-for="(r, i) in box.recipe.slice(0,1)" :key="i">
                    <li><!--  v-if="i==0" 첫번째 레시피 이미지 -->
                      <p><img
                        :src="r.mainImgId?`http://localhost:8090/file/download/thumbnail?fileId=${r.mainImgId}`:mainPicture"
                        width="200px" 
                        height="150px" 
                        @error="setEmptyImg"
                      /></p>
                    </li>
                  </ul>
                  {{box.name}} {{box.recipe.length}}<br/>
                </li>
                <li v-else><!-- 레시피가 없는 박스 -->
                  <p><img
                    :src="mainPicture" 
                    width="200px" 
                    height="150px" 
                    @error="setEmptyImg"
                  /></p>
                  {{box.name}} 0<br/>
                  </li>
              </ul>
            </router-link>
          </td>
        </tr>
        <tr>          
          <td>
            <confirm-input
              :text="'+ '+$t('button.addNewBox')"
              :title="$t('button.addNewBox')"
              :value="boxName"
              :callback="text => addNewBox(text)"
            />
            <p>
              <img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg">
            </p>
            <br/>
          </td>
        </tr>
    </div>
    <!--FOOTER-->
  </div>
</template>

<script>
import emptyImg from '@/assets/emptyImg.png'
import ConfirmInput from 'vue-confirm-input'

export default {
  name : "RecipeBoxListView",
  data: ()=>({
    recipeBoxes : [],
    mainPicture : '',
    boxName: '기본박스',
  }),

  created() {
    this.initialize();
  },
  
  mounted() {
    this.$checkToken('recipeboxlist');
  },

  components: {
      ConfirmInput
  },

  methods: {
    initialize() {
      this.getRecipeBoxAll();
    },

    async getRecipeBoxAll() {      
      const response = await this.$api(
        `http://localhost:8090/api/recipebox/mine`,
        "get"
      );
      if (response.status === this.HTTP_OK) {
        this.recipeBoxes = response.data;
        console.log(this.recipeBoxes);
      }
    },

    callEdit() {
      console.log("Edit");
      // TODO : 폴더 편집 - 폴더명 변경, 삭제, 폴더 새로 만들기 기능
    },

    async addNewBox(name) {
        console.log("addNewBox : "+name);
        this.boxName = name;
        const response = await this.$api(
          `http://localhost:8090/api/recipebox/${name}`,
          "post"
        );
        if (response.status === this.HTTP_OK) {
            this.selectedRecipeBox = response.data;
        }
        this.initialize();
    },

    setEmptyImg(e) {
      e.target.src=emptyImg;
    },
    async getFileId(id){
        const response = await this.$api(
          `http://localhost:8090/file?contentsId=${id}`,
          "get"
        );
        if (response.status === this.HTTP_OK) {
            console.log(response.data);
        }
    }
  },
}
</script>

<style scoped>
button
{
    background: inherit ; 
    border:none; 
    box-shadow:none; 
    border-radius:0; 
    padding:0; 
    overflow:visible; 
    cursor:pointer
}
ul {
    list-style: none;
}
li {
    float: left;
}
</style>
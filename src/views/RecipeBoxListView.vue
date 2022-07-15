<template>
  <div>
    <!--HEADER-->
    <h4>{{ $t("title.recipeBox") }}</h4>
    <div class="menu">
        <ul>
            <li><a href="/myrecipe">{{ $t("menu.myRecipe") }}</a></li>
            <li><a href="/recipeboxlist">{{ $t("menu.savedRecipe") }}</a></li>
            <li><a href="#">{{ $t("menu.historyRecipe") }}</a></li>
        </ul>
    </div>
    <!--CONTENT-->
    <div class="contents">        
        <button @click="callEdit">{{$t("button.edit")}}</button>
        <tr v-for="(item, index) in recipeRecipeBoxes" :key="index">
          <td v-if="item.isDefault == true">
            <router-link :to="'/recipebox/'+item.id">
              <p>
                <img :src=item.fileId width="200px" height="150px" @error="setEmptyImg">
              </p>
              {{item.name}} {{item.recipesCount}}<br/>
            </router-link>
          </td>
        </tr>
        <tr v-for="(item, index) in recipeRecipeBoxes" :key="`o-${index}`">
          <td v-if="item.isDefault == false">
            <router-link :to="'/recipebox/'+item.id">
              <p>
                <img :src=item.fileId width="200px" height="150px" @error="setEmptyImg">
              </p>
              {{item.name}} {{item.recipesCount}}<br/>
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
import axios from "axios"
import emptyImg from '@/assets/emptyImg.png'
import ConfirmInput from 'vue-confirm-input'

export default {
  name : "RecipeBoxListView",
  data: ()=>({
    recipeBoxes : [],
    recipeRecipeBoxes : [],
    mainPicture : '',
    boxName: '기본박스',
  }),

  created() {
    this.initialize();
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
        "http://localhost:8090/api/recipebox/all",
        "get",
      );
      if (response.status === this.HTTP_OK) {
        this.recipeBoxes = response.data;
        console.log(response.data);
      }
      this.getRecipeRecipeBox();
    },

    async getRecipeRecipeBox() {
      if(this.recipeBoxes.length > 0)
      {
        var i = 0;
        this.recipeRecipeBoxes = [];
        while(i < this.recipeBoxes.length ){
          const response = await this.$api(
            `http://localhost:8090/api/reciperecipebox`,
            "get",
            { box: this.recipeBoxes[i].id }
          );

          if (response.status === this.HTTP_OK) {
            
            this.recipeRecipeBoxes.push({
              id: this.recipeBoxes[i].id,
              name: this.recipeBoxes[i].name,
              recipesCount: response.data.length,
              isDefault: this.recipeBoxes[i].isDefault,
              fileId: 'http://localhost:8090/file/download?fileId=' + 4, // TODO: API 사용해 fileId 얻어오기
              data: response.data
            });
            console.log(response.data);
          }
          i = i + 1;
        }
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
  },
}
</script>

<style>
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
</style>
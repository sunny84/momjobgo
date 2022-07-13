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
        <tr v-for="(item, index) in recipeBoxes" :key="index">
          <td v-if="item.isDefault == true">
            <a :href="`/recipebox`" target="_blank"> <!--TODO: a herf="레시피 박스 상세 페이지로 이동"-->
              <p>
                <img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg">
              </p>
              {{item.name}} {{item.recipeEntities.length}}<br/>
            </a>
          </td>
        </tr>
        <tr v-for="(item, index) in recipeBoxes" :key="`o-${index}`">
          <td v-if="item.isDefault == false">
            <a :href="`/recipebox`" target="_blank" @click="link"> <!--TODO: a herf="레시피 박스 상세 페이지로 이동"-->
              <p>
                <img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg">
              </p>
              {{item.name}} {{item.recipeEntities.length}}<br/>
            </a>
          </td>
        </tr>
        <tr>          
          <td>
            <a href="#" target="_blank" @click="addNewBox"> <!-- TODO: add new box -->
              <p>
                <img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg">
              </p>
              <br/>
            </a>
          </td>
        </tr>
    </div>
    <!--FOOTER-->
  </div>
</template>

<script>
import axios from "axios"
import emptyImg from '@/assets/emptyImg.png'

export default {
  name : "RecipeBoxListView",
  data: ()=>({
    recipeBoxes : [],
    mainPicture : ''
  }),

  created() {
    this.initialize();
  },

  methods: {
    initialize() {
      this.callRecipeBox();
    },

    async callRecipeBox() {
      await axios.get(`http://localhost:8090/recipebox/all`, {
      }).then(response=>{
        this.recipeBoxes = response.data;
        console.log("contents:", response.data);
      }).catch(error=>{
        console.error(error);
      })
    },
    callEdit() {
      console.log("Edit");
    },
    addNewBox() {
      console.log("addNewBox");
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
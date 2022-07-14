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
            <a :href="`/recipebox`" target="_blank"> <!--TODO: a herf="레시피 박스 상세 페이지로 이동"-->
              <p>
                <img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg">
              </p>
              {{item.name}} {{item.recipesCount}}<br/>
            </a>
          </td>
        </tr>
        <tr v-for="(item, index) in recipeRecipeBoxes" :key="`o-${index}`">
          <td v-if="item.isDefault == false">
            <a :href="`/recipebox`" target="_blank"> <!--TODO: a herf="레시피 박스 상세 페이지로 이동"-->
              <p>
                <img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg">
              </p>
              {{item.name}} {{item.recipesCount}}<br/>
            </a>
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
    writer : 1,
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
      this.callRecipeBox();
    },

    callRecipeBox() {
      this.getRecipeBoxAll();
    },

    async getRecipeBoxAll() {
      await axios.get(`http://localhost:8090/recipebox/all`, {
      }).then(response=>{
        this.recipeBoxes = response.data;
        // console.log("recipebox:", response.data);
      }).catch(error=>{
        console.error(error);
      })
      this.getRecipeRecipeBox();
    },

    async getRecipeRecipeBox() {
      if(this.recipeBoxes.length > 0)
      {
        var i = 0;
        this.recipeRecipeBoxes = [];
        while(i < this.recipeBoxes.length ){
          await axios.get(`http://localhost:8090/reciperecipebox/box=${this.recipeBoxes[i].id}`, { //&user=${this.writer}`, {
          }).then(response=>{
              this.recipeRecipeBoxes.push({
                id: this.recipeBoxes[i].id,
                name: this.recipeBoxes[i].name,
                recipesCount: response.data.length,
                isDefault: this.recipeBoxes[i].isDefault,
                // img:,
                data: response.data
              });
          }).catch(error=>{
              console.error(error);
          })
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
        await axios.post(`http://localhost:8090/recipebox/name=${name}&user=${this.writer}`, {
        }).then(response=>{
            this.selectedRecipeBox = response.data;
            this.writer = this.selectedRecipeBox.userId;
        }).catch(error=>{
            console.error(error);
        })
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
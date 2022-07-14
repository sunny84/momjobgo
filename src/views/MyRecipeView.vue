<template>
    <!--HEADER-->
    <!--CONTENT-->
    <div class="contents">        
        <h4>{{$t("content.myRecipe")}} {{ list.length }}</h4>
        <button @click="callWrite">{{$t("button.write")}}</button>
        <tr v-for="(item, index) in list" :key="index">
          <td>
            <a :href="item.name" target="_blank"> <!--TODO: a herf="레시피 상세페이지로 이동"-->
              <p>
                <img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg">
              </p>
              {{item.title}} <button @click="callRecipeBox(item.recipeId)">ㅁ</button><br/>
              {{item.subTitle}}<br/>
              {{item.timeTaken}} ★{{item.score}}.0 ... ({{item.commentsNumber}})<br/>
            </a>
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
    created() {
      this.initialize();
    },
    methods : {
      initialize() {
        this.callContents();
        console.log(this.list.length);
      },
      async callContents(recipeId) {
        // console.log(`callContents ${this.list.length}`);
        // TODO:
        // const response = await this.$api(`/contents/writer=1`,"get", {
        //   // writer: this.writer
        // });
        // this.contents = response.data;
        // console.log(response.data);
        await axios.get(`http://localhost:8090/contents/writer=${this.writer}`, {
        }).then(response=>{
          this.contents = response.data;
          // console.log("contents:", response.data);
        }).catch(error=>{
          console.error(error);
        })

        if(this.contents.length > 0)
        {
          var i = 0;
          this.list = [];
          while(i < this.contents.length ){
            // console.log(i,"회");
            await axios.get(`http://localhost:8090/Recipe/contents=${this.contents[i].id}`, {
            }).then(response=>{
              this.recipe = response.data
              // console.log("Recipe:", response.data);
            }).catch(error=>{
              console.error(error);
            })
            await axios.get(`http://localhost:8090/score/recipe=${this.recipe[0].id}`, {
            }).then(response=>{
              this.score[i] = response.data.score;
              // console.log("score:", response.data);
            }).catch(error=>{
              console.error(error);
            })
            await axios.get(`http://localhost:8090/time-taken/id=${this.recipe[0].timeTakenId}`, {
            }).then(response=>{
              this.timeTaken[i] = response.data.time;
              // console.log("timeTaken:", response.data);
            }).catch(error=>{
              console.error(error);
            })

            this.list.push({
              title: this.contents[i].title,
              subTitle: this.contents[i].subTitle,
              score: this.score[i],
              timeTaken: this.timeTaken[i],
              recipeId: this.recipe[0].id,
              commentsNumber : 66   // TODO: comments
            });

            i = i + 1;          
          }
          // console.log(`list: ${this.list}`);
        } 
      },
      callWrite() {
        console.log("write button");
      },
      async callRecipeBox(recipeId) {
        console.log("recipebox save button")
        // 기본박스에 저장
        // isDefault = true 인 id 에 저장        
        await axios.get(`http://localhost:8090/recipebox/default`, {
        }).then(response=>{
            this.defaultRecipeBox = response.data;
            // console.log("contents:", response.data);
        }).catch(error=>{
            console.error(error);
        })

        await axios.post(`http://localhost:8090/reciperecipebox/${this.defaultRecipeBox.id}?recipe=${recipeId}&user=${this.writer}`, {
        }).then(response=>{
            console.log("contents:", response.data);
        }).catch(error=>{
            console.error(error);
        })
      },
      setEmptyImg(e) {
        e.target.src=emptyImg;
      },
    },
}
</script>
<style>
ul{
 list-style:none;
 padding-left:0px;
}
a:link{color:black;text-decoration:none;}
a:visited{color:gray;text-decoration:none;}
a:hover{color:orange;text-decoration:none;}
</style>

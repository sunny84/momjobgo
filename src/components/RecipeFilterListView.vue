<template>
  <div class="filterList">
    <div v-for="(recipe, $index) in recipeList" :key="$index">
        <div v-if="recipe.file_id!=null">
          <img :src="getImgURL(recipe.file_id)" width="200px" height="150px" @error="setEmptyImg" />
        </div>
      <div v-else style="width:200px; height:150px; background-color:#EEEEEE;" >
        이미지 없음        
      </div>
      <span v-if="recipe.subscribe!=null">구독중</span>
      <!-- <span v-else>구독아님</span> -->
      <br/>
      <span>{{recipe.title}}</span><br/>
      <span>{{recipe.sub_title}}</span>
      <div>
        <span>{{$t(`option.timeTaken[${recipe.time_taken_id}]`)}}</span>&nbsp;
        <span>{{recipe.score}}</span>&nbsp;
        <span>0</span>
      </div>
    </div>
    <div>
      <infinite-loading @infinite="infiniteHandler"></infinite-loading>
    </div>
  </div>
</template>

<script>
import InfiniteLoading from 'vue-infinite-loading';
import { mapGetters } from "vuex";
import emptyImg from "@/assets/emptyImg.png";

export default {
  name : "RecipeFilterListView",
  data: ()=>({
    page : 0,
    recipeList : [],
  }),

  computed : {  
    ...mapGetters('filter', ['period', 'timeTaken', 'ingredientIds', 'sort'])
  },

  components: {
    InfiniteLoading
  },

  methods: {

    getImgURL(id) {
      const url = 'http://localhost:8090/file/download?fileId=' + id;
      console.log(url);
      return url
    },

    setEmptyImg(e) {
      e.target.src = emptyImg;
    },

    async infiniteHandler($state){
      let params = `page=${this.page}`;
      if(this.period > -1) params += `&period=${this.period}`;
      if(this.timeTaken > -1) params += `&timeTakenId=${this.timeTaken}`;
      if(this.ingredientIds.length > 0){
        params += `&Ids=`;
        for(let i=0;i<this.ingredientIds.length;i++){
          params += this.ingredientIds[i];
          if(i < this.ingredientIds.length-1) params += ',';
        }
      }
      if(this.sort == 1) params += '&sort=1';
      console.log(params);
      const response = await this.$api(`http://localhost:8090/api/Recipe/filter?`+params, `get`);
      if (response.status === this.HTTP_OK) {
        if(response.data.length){
          // console.log(response.data);
          for(const data of response.data){
            this.recipeList.push(data);
          }          
          this.page++;
          $state.loaded();
          if(response.data.length / 2 < 1){
            $state.complete();
          }
        }else{
          $state.complete();
        }
      }else{
        console.log(response.status);
        $state.complete();
      }
    },
  },
}
</script>

<style>
	.filterList {
		display: grid;
		grid-template-columns: repeat(2, 200px);
		/* grid-auto-rows: 65px; */
		grid-gap: 20px;
	}
</style>
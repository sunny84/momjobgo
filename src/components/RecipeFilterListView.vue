<template>
  <div>
    <div v-if="listView == 0" class="wrap_list">
      <div v-for="(recipe, $index) in recipeList" :key="$index">
        <div class="contents1">
          <div class="squre2">{{ $t(`option.period_s[${recipe.period}]`)[0] }}</div>
          <div v-if="recipe.subscribe!=null" class="bookmark"><img src="@/assets/images/bul_bookmark.png"></div>
          <div v-else class="bookmark"><img src="@/assets/images/bul_bookmark2.png"></div>
          <ul class="wrap_faces">
            <div class="good facebg"></div>
            <div class="number">60</div>
          </ul><!--wrap_faces-->
          <img v-if="recipe.file_id != null" class="pic" :src="getImgURL(recipe.file_id)" @error="setEmptyImg">
          <img v-else class="pic" src="@/assets/emptyImg.png">
          <div class="text">
            <div class="title">{{recipe.title}}</div>
            <div class="longtext">{{recipe.sub_title}}</div>
            <div class="wrap_info">
              <span class="bullet clock">{{$t(`option.timeTaken_s[${recipe.time_taken_id}]`)}}</span>
              <span class="bullet star">{{(recipe.score == null ? 0 : recipe.score).toFixed(1)}}</span>
              <span class="bullet chat">187 </span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-else class="wrap_contents">    
      <div v-for="(recipe, $index) in recipeList" :key="$index">
        <div class="contents1">
          <ul class="wrap_faces">
            <div class="good facebg"></div>
            <div class="number">60</div>
          </ul><!--wrap_faces-->
          <img v-if="recipe.file_id != null" class="pic" :src="getImgURL(recipe.file_id)" @error="setEmptyImg">
          <img v-else class="pic" src="@/assets/emptyImg.png">
          <div class="text">
            <div class="squre3 fl">{{ $t(`option.period_s[${recipe.period}]`)[0] }}</div>
            <div class="title fl">{{recipe.title}}</div>
            <div class="longtext fl">{{recipe.sub_title}}</div>
            <div v-if="recipe.subscribe!=null" class="bookmark1 fr"></div>
            <div v-else class="bookmark2 fr"></div>
            <div class="wrap_info fr">
              <span class="bullet clock">{{$t(`option.timeTaken_s[${recipe.time_taken_id}]`)}}</span>
              <span class="bullet star">{{(recipe.score == null ? 0 : recipe.score).toFixed(1)}}</span>
              <span class="bullet chat">187 </span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div>
      <infinite-loading @infinite="infiniteHandler">
        <div slot="no-more"><br/></div>
      </infinite-loading>
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
    ...mapGetters('filter', ['period', 'timeTaken', 'ingredientIds', 'sort', 'listView'])
  },

  components: {
    InfiniteLoading
  },

  methods: {

    getImgURL(id) {
      const url = `${this.$API_SERVER}/file/download/thumbnail?fileId=` + id;
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
      const response = await this.$api(`${this.$API_SERVER}/api/Recipe/filter?`+params, `get`);
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
	/* .filterList {
		display: grid;
		grid-template-columns: repeat(2, 48%);
		grid-auto-rows: 65px;
		grid-gap: 20px;
	} */
</style>
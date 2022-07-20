<template>
  <div class="SubscribeList">
    <hooper :autoPlay="true" :playSpeed="2000" id="hooper">
      <slide v-for="(recipe, $index) in recipeList" :key="$index" class="slide">
        <div v-if="recipe.file_save_name!=null">
          <img :src="recipe.file_save_name" width="200px" height="150px" @error="setEmptyImg" />
        </div>
        <div v-else style="width:200px; height:150px; background-color:#EEEEEE;" >
          이미지 없음 {{$index}}    
        </div>
        <span>{{recipe.title}}</span>
        <div>
          <span>{{$t(`option.timeTaken[${recipe.time_taken_id}]`)}}</span>&nbsp;
          <span>{{recipe.score}}</span>&nbsp;
          <span>0</span>
        </div>
      </slide>
    </hooper>
  </div>
</template>

<script>
import { Hooper, Slide } from 'hooper';
import 'hooper/dist/hooper.css';
import emptyImg from "@/assets/emptyImg.png";

export default {
  name : "RecipeSubscribeListView",
  data: ()=>({
    period : 0,
    recipeList : [],
  }),

  components: {
    Hooper,
    Slide,
  },

  created() {
    this.initialize();
  },

  methods: {
    initialize() {
      this.callSubscribeList();
    },

    setEmptyImg(e) {
      e.target.src = emptyImg;
    },

    async callSubscribeList(){
      let params = `period=${this.period}`;
      const response = await this.$api(`http://localhost:8090/api/reciperecipebox/subscribe`, `get`);
      if (response.status === this.HTTP_OK) {
        this.recipeList = response.data;
      }
    },
  },
}
</script>

<style>

/* #hooper {
  height: 10vh;
}
.slide {
  background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
  font-size: 4rem;
  line-height: 10vh;
} */
</style>
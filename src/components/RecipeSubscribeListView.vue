<template>
  <div class="SubscribeList">
    <div class="wrap_subsc">
      <ul>
        <li class="title">{{ $t(`title.subscribeRecipe`) }}</li>
        <li class="menu n1" :class="{ on : period == 0 }" @click="setPeriod(0)">{{ $t(`option.period_s[0]`)[0] }}</li>
        <li class="menu n2" :class="{ on : period == 1 }" @click="setPeriod(1)">{{ $t(`option.period_s[1]`)[0] }}</li>
        <li class="menu n3" :class="{ on : period == 2 }" @click="setPeriod(2)">{{ $t(`option.period_s[2]`)[0] }}</li>
        <li class="n4">{{ $t(`menu.allList`) }} </li>
      </ul>
    </div><!--end.wrap_subsc-->

    <hooper id="hooper">
        <slide v-for="(recipe, $index) in recipeList" :key="$index" class="slide">
          <div class="wrap_slide_pic">
            <div class="contents">
              <div class="squre">{{ $t(`option.period_s[${recipe.period}]`)[0] }}</div>
              <div class="bookmark"><img src="@/assets/images/bul_bookmark.png"></div>
              <div class="wrap_faces" style="position: absolute;left:8px; top:40px;">
                <ul class="fl">
                  <div class="good facebg"></div>
                  <div class="number">609</div>
                </ul>
                <ul class="fl">
                  <div class="bad facebg"></div>
                  <div class="number">609</div>
                </ul>  
              </div>
              <img v-if="recipe.file_id != null" class="pic" :src="getImgURL(recipe.file_id)" @error="setEmptyImg">
              <img v-else class="pic" src="@/assets/emptyImg.png">
              <div class="text">
                <div class="title">{{recipe.title}}</div>
                <div class="wrap_info">
                  <span class="bullet clock">{{$t(`option.timeTaken_s[${recipe.time_taken_id}]`)}}</span>
                  <span class="bullet star">{{(recipe.score == null ? 0 : recipe.score).toFixed(1)}}</span>
                  <span class="bullet chat">187</span>
                </div>
              </div><!--text-->
            </div><!--contents-->
          </div>
      </slide>
      <hooper-pagination slot="hooper-addons"></hooper-pagination>
    </hooper>
  </div>
</template>

<script>
import { Hooper, Slide, Pagination as HooperPagination } from 'hooper';
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
    HooperPagination,
  },

  created() {
    this.initialize();
  },

  methods: {
    initialize() {
      this.callSubscribeList();
    },

    getImgURL(id) {
      const url = `${this.$API_SERVER}/file/download/thumbnail?fileId=` + id;
      console.log(url);
      return url
    },

    setEmptyImg(e) {
      e.target.src = emptyImg;
    },

    setPeriod(period) {
      this.period = period;
      this.callSubscribeList();
    },

    async callSubscribeList(){
      let params = `period=${this.period}`;
      const response = await this.$api(`${this.$API_SERVER}/api/reciperecipebox/subscribe?${params}`, `get`);
      if (response.status === this.HTTP_OK) {
        this.recipeList = response.data;
      }
    },
  },
}
</script>

<style>

 #hooper {
  height: 100%;
  padding:0 0 20px 0;
}
/*.slide {
  background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
  font-size: 4rem;
  line-height: 10vh;
} */
</style>
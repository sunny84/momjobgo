<template>
  <div>
    <main class="recipebox">
    <!--HEADER-->
    <h1 class="fl"><router-link :to="'/recipedetail/'+recipeId"><img src="@/assets/images/icon_back.png" alt="돌아가기" title="돌아가기"/></router-link>
      <span class="color-orange padding-left-15">{{ $t("title.recipeBox") }}</span>
    </h1>
    <div class="wrap_menu">
        <ul>
            <li class="menu" :class="{on : this.$route.path == '/myrecipe'}" style="cursor: ponter;" onclick="location.href='/myrecipe';">{{ $t("menu.myRecipe") }}</li><!--<router-link :to="'/myrecipe'"></router-link>-->
            <li class="menu" :class="{on : this.$route.path == '/recipeboxlist'}" style="cursor: ponter;" onclick="location.href='/recipeboxlist';">{{ $t("menu.savedRecipe") }}</li><!--<router-link :to="'/recipeboxlist'"></router-link>-->
            <li class="menu" :class="{on : this.$route.path == '/todaySawRecipe'}" style="cursor: ponter;" onclick="location.href='/todaySawRecipe';">{{ $t("menu.historyRecipe") }}</li><!--<router-link :to="'#'"></router-link>-->
        </ul>
    </div>
    <!--CONTENT-->
    </main>
  </div>
</template>
<script>
export default {
  name : "TodaySawRecipeView",
  data: ()=>({
    recipeId: 47,
  }),
  components: {
      ConfirmInput
  },
  created() {
    this.initialize();
  },
  mounted() {
    this.$checkToken('todaySawRecipe');
  },
  methods: {
    initialize(){
      console.log("initialize")
    }
  },
  watch:{
    recipeId(newValue){
      console.log("recipeId: ", newValue)
    }
  }
}

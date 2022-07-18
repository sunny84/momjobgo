<template>
    <div>
        <h4>우리아기,</h4>
        <h4>어떤 요리가 좋을까요?</h4>
        <div>
          <button @click="search">필터</button>
          <button>최신순</button>
          <button>별점순</button>
          <button>후기순</button>
          <button>재료명순</button>
        </div>  
        <div>
          <div v-for="(recipe, i) in recipeList" :key="i">
            <span>{{recipe.title}}</span><br/>
            <span>{{recipe.sub_title}}</span>
            <div>
              <span>{{$t(`option.timeTaken[${recipe.time_taken_id}]`)}}</span>&nbsp;
              <span>{{recipe.score}}</span>&nbsp;
              <span>0</span>
            </div>
          </div>
        </div>     
    </div>
</template>

<script>
export default {
  name : "RecipeListView",
  data: ()=>({
    page : 0,
    recipeList : [],
  }),

  created() {
    this.initialize();
  },

  methods: {
    initialize() {
      this.callRecipeFilter();
    },

    search(){
      this.callRecipeFilter();
    },

    async callRecipeFilter() {
      const params = `page=${this.page}`

      const response = await this.$api(`http://localhost:8090/Recipe/api/filter?`+params, `get`);

      if (response.status === this.HTTP_OK) {
        this.recipeList = response.data;
      }
    },
  },
}
</script>

<style>
</style>
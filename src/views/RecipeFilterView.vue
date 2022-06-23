<template>
    <div>
        <h4>{{ $t("title.RecipeFilter") }}</h4>
        <div v-for="item in $t('option.period')" :key="item.i">
          <button>{{item}}</button>
        </div>
        <hr/>
        <div v-for="item in $t('option.timeTaken')" :key="item.i">
          <button>{{item}}</button>
        </div>
        <hr/> 
        <div>
            <div v-for="(category, i) in ingredientCategory" :key="i" v-show="category.id!==6">
                <p v-show="category.id!==1">{{category.name}} - {{$t(("ing_category["+category.id+"]"))}}</p>
                <p>
                    <span v-for="(item, idx) in category.ingredientEntities" :key="item.id">
                        <input  id="ing_name"  type="checkbox" v-model="selectedIngredients" :value="item.id">
                        <!-- match i18n item & key value : OK-->
                        <label for="ing_name">{{$t("ingredient."+[item.key])}}</label>
                        <br v-if="category.id===1&&idx%3===1">
                        <br v-else-if="category.id!==1&&idx%3===2">
                    </span>
                </p>
            </div>

        </div>        
    </div>
</template>

<script>
export default {
  name : "RecipeFilterView",
  data: ()=>({
    ingredientCategory : [],
  }),

  created() {
    this.initialize();
  },

  methods: {
    initialize() {
      this.callIngredientCategory();
    },

    async callIngredientCategory() {
      const response = await this.$api("http://localhost:8090/Ingredient/join/category", "get");

      if (response.status === this.HTTP_OK) {
        this.ingredientCategory = response.data;
      }
    },
  },
}
</script>

<style>
</style>
<template>
  <div>
    <h4>{{ $t("title.RecipeFilter") }}</h4>
    <div v-for="(item, idx) in $t('option.period')" :key="item.i">
      <button @click="setPeriod(idx)">{{item}}</button>
    </div>
    <hr/>
    <div v-for="(item, idx) in $t('option.timeTaken')" :key="item.i">
      <button @click="setTimeTaken(idx)">{{item}}</button>
    </div>
    <hr/> 
    <div v-for="(category, idx) in ing_data" :key="idx" v-show="category.id !== 6">
      <p v-show="category.id !== 1">{{ $t("ing_category." + [category.key]) }}</p>
      <p>
        <span v-for="(item, idx) in category.ingredientEntities" :key="idx">
          <input
            :id="`${item.key}`"
            type="checkbox"
            v-model="selectedIngredientIds"
            :value="item.id"
          />
          <label :for="`${item.key}`">{{ $t("ingredient." + [item.key]) }}</label>
          <br v-if="category.id === 1 && idx % 3 === 1" />
          <br v-else-if="category.id !== 1 && idx % 3 === 2" />
        </span>
      </p>
    </div> 
    <div>
      <button @click="searchFilterList">검색</button>
    </div>     
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name : "RecipeFilterView",
  data: ()=>({
    ing_data : [],
    selectedIngredientIds: [],
  }),

  created() {
    this.initialize();
  },

  computed : {  
    ...mapGetters('filter', ['period', 'timeTaken', 'ingredientIds'])
  },

  methods: {
    ...mapActions('filter', ['setPeriod','setTimeTaken', 'setIngredientIds']),

    initialize() {
      this.callIngredientCategory();
    },

    async callIngredientCategory() {
      const response = await this.$api("http://localhost:8090/Ingredient/join/category", "get");

      if (response.status === this.HTTP_OK) {
        this.ing_data = response.data;
      }
    },

    searchFilterList() {
      this.setIngredientIds(this.selectedIngredientIds);
      this.$router.push('recipelist')
    },
  },
}
</script>

<style>
</style>
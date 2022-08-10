<template>
  <div>
    <main class="filter">
      <h1 class="fl"><router-link to="/recipelist"><img src="@/assets/images/icon_back.png" alt="돌아가기" title="돌아가기" /></router-link><span class="color-orange padding-left-15">{{ $t("title.RecipeFilter") }}</span></h1>
      <div class="fr margin-top-20" @click="clearFilter">초기화</div>
      <div class="wrap_period">
        <dl class="period1" v-for="(item, idx) in $t('option.period_s')" :key="item.i">
          <dt class="menu" :class="{on : selectedPeriod == idx}" @click="setSelectedPeriod(idx)">{{item[0]}}</dt><dd class="month">{{item[1]}}</dd>
        </dl>
      </div><!--wrap_period-->
      <h2><span class="title">소요시간</span></h2>
      <div class="wrap_time">
        <VueSlideBar v-model="slider.value"
          :data="slider.data" 
          :range="slider.range"
          :lineHeight="10"
          :processStyle="{ backgroundColor: '#028870' }">
          <template slot="tooltip">
            <span class="circle" style="position:absolute ;left:0;top:14px;"></span>
          </template>
        </VueSlideBar>
      </div>
      <div v-for="(category, idx) in ing_data" :key="idx" v-show="category.id !== 6">
        <h2><span class="title">{{ $t("ing_category2." + [category.key])[0] }}</span>
        <span class="title2">{{ $t("ing_category2." + [category.key])[1] }}</span></h2>
        <div class="wrap_select">
          <span  v-for="(item, idx) in category.ingredientEntities" :key="idx">
            <span class="food" :class="{on : selectedIngredientIds.includes(item.id)}" @click="setSelectedIng(item.id)">{{$t('ingredient.' + [item.key])}}</span>
          </span>
        </div>
      </div>
      <div class="btn_apply" @click="searchFilterList">적용하기</div>
    </main>    
  </div>
</template>

<script>
import VueSlideBar from 'vue-slide-bar'
import { mapActions, mapGetters } from "vuex";

export default {
  name : "RecipeFilterView",
  data: ()=>({
    ing_data: [],
    selectedIngredientIds: [],
    selectedPeriod: -1,
    slider: {
      value: -1,
      data: [],
      range: [],
    },
  }),

  created() {
    this.initialize();
  },

  computed : {  
    ...mapGetters('filter', ['period', 'timeTaken', 'ingredientIds'])
  },

  components: {
    VueSlideBar
  },

  methods: {
    ...mapActions('filter', ['setPeriod','setTimeTaken', 'setIngredientIds']),

    initialize() {
      for(let i = 0; i < this.$i18n.t("option.timeTaken_s").length; i++){
        this.slider.data.push(i);
        this.slider.range.push({label: this.$i18n.t(`option.timeTaken_s[${i}]`)})
      }
      this.selectedPeriod = this.period;
      this.slider.value = this.timeTaken;
      this.selectedIngredientIds = this.ingredientIds;
      this.callIngredientCategory();
    },

    clearFilter() {

    },

    setSelectedPeriod(idx){
      if(this.selectedPeriod == idx){
        this.selectedPeriod = -1;
      }else{
        this.selectedPeriod = idx;
      }
    },

    setSelectedIng(idx) {
      if(this.selectedIngredientIds.indexOf(idx) >= 0) {
        this.selectedIngredientIds.splice(this.selectedIngredientIds.indexOf(idx), 1);
      }else{
        this.selectedIngredientIds.push(idx);
      }
    },

    async callIngredientCategory() {
      const response = await this.$api(`${this.$API_SERVER}/Ingredient/join/category`, "get");

      if (response.status === this.HTTP_OK) {
        this.ing_data = response.data;
      }
    },

    searchFilterList() {
      this.setIngredientIds(this.selectedIngredientIds);
      this.setPeriod(this.selectedPeriod);
      this.setTimeTaken(this.slider.value);
      this.$router.push('recipelist');
    },
  },
}
</script>

<style>
</style>
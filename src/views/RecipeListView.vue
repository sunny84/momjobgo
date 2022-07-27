<template>
  <div>
    <main>
      <div class="wrap">
        <h1><span class="color-orange">우리아기,</span> 
          <br>어떤 요리가 좋을까요?</h1>
        <SubscribeListView></SubscribeListView>
        <div class="wrap_filter">
          <div class="wrap_filter_menu">
            <ul class="fl">
              <li class="title"><router-link to="/filter">필터</router-link></li>
              <li class="menu" :class="{ on : sort == 0 }" @click="setSort(0)">최신순</li>
              <li class="menu" :class="{ on : sort == 1 }" @click="setSort(1)">별점순</li>
              <li class="menu">후기순</li>
              <li class="menu">재료명순</li>
            </ul>
            <ul class="fr">
              <li class="view n1">모아보기</li>
              <li class="view n2">크게보기</li>
            </ul>
          </div>
        </div>
        <FilterListView :key="sort"></FilterListView>
      </div>
    </main>
  </div>
</template>

<script>
import SubscribeListView from "../components/RecipeSubscribeListView.vue";
import FilterListView from "../components/RecipeFilterListView.vue";
import { mapActions, mapGetters } from "vuex";
export default {
  name : "RecipeListView",
  data: ()=>({
  }),

  components: {
    SubscribeListView,
    FilterListView,
  },

  computed : {  
    ...mapGetters('filter', ['sort'])
  },

  methods: {
    ...mapActions('filter', ['setSort']),
  },
}
</script>

<style>
</style>
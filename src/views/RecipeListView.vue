<template>
  <div>
    <div>
      <h4>우리아기,</h4>
      <h4>어떤 요리가 좋을까요?</h4>
      <div>
        <router-link to="/filter">필터</router-link>&nbsp;
        <button @click="setSort(0)">최신순</button>&nbsp;
        <button @click="setSort(1)">별점순</button>&nbsp;
        <button>후기순</button>&nbsp;
        <button>재료명순</button>
      </div>
    </div>
    <FilterListView :key="sort"></FilterListView>
  </div>
</template>

<script>
import FilterListView from "../components/RecipeFilterListView.vue";
import { mapActions, mapGetters } from "vuex";
export default {
  name : "RecipeListView",
  data: ()=>({
  }),

  components: {
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
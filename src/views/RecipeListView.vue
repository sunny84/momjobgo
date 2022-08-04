<template>
  <div>
    <main>
      <h1><span class="color-orange">{{$t(`title.myBaby`)}},</span> 
        <br>{{$t(`title.whatRecipe`)}}</h1>
      <SubscribeListView></SubscribeListView>
      <div class="wrap_filter">
        <div class="wrap_filter_menu">
          <ul class="fl">
            <li class="title"><router-link to="/filter">{{$t(`title.RecipeFilter`)}}</router-link></li>
            <li class="menu" :class="{ on : sort == 0 }" @click="setSort(0)">{{$t(`orderBy.newest`)}}</li>
            <li class="menu" :class="{ on : sort == 1 }" @click="setSort(1)">{{$t(`orderBy.topRated`)}}</li>
            <li class="menu">{{$t(`orderBy.mostRated`)}}</li>
            <li class="menu">{{$t(`orderBy.ingredients`)}}</li>
          </ul>
          <ul class="fr">
            <li class="view n1" :class="{ on : listView == 0 }" @click="setListView(0)">모아보기</li>
            <li class="view n2" :class="{ on : listView == 1 }" @click="setListView(1)">크게보기</li>
          </ul>
        </div>
      </div>
      <FilterListView :key="sort"></FilterListView>
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

  mounted() {
    this.$checkToken("recipelist");
  },

  computed : {  
    ...mapGetters('filter', ['sort', 'listView'])
  },

  methods: {
    ...mapActions('filter', ['setSort', 'setListView']),
  },
}
</script>

<style>
</style>
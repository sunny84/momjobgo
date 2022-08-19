<template>
  <div>
    <main class="recipebox">
    <!--HEADER-->
    <BoxListMenu :key="recipeId"></BoxListMenu>
    <!--CONTENT-->
      <div>
          <div class="wrap_allnum fl">
              <span class="dp-inline-block fl">오늘 본 레시피</span>
          </div>
      </div>
    </main>
  </div>
</template>
<script>
import BoxListMenu from '@/components/BoxListMenu.vue';
import { mapActions, mapGetters } from "vuex";

export default {
  name : "TodaySawRecipeView",
  data: ()=>({
  }),
  components: {
    BoxListMenu
  },
  created() {
  },
  mounted() {
    this.$checkToken('todaySawRecipe');
  },
  computed : {
    ...mapGetters('box', ['recipeId'])
  },
  methods: {
    ...mapActions('box', ['setRecipeId']),
  },
}

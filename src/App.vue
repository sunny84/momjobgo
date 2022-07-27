<template>
  <div id="app" class="wrap">
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/write">{{ $t("title.writeRecipe") }}</router-link> |
      <router-link to="/recipedetail/26">{{ $t("title.viewRecipe") }}</router-link> |
      <router-link to="/recipeboxlist">{{ $t("title.recipeBox") }}</router-link> |
      <router-link to="/login">login</router-link> |
      <router-link to="/about">About</router-link> |
      <!-- <router-link to="/filter">{{ $t("title.RecipeFilter") }}</router-link> | -->
      <router-link to="/recipelist">RecipeList</router-link>
    </nav>
    <div>
      <p>
        <button @click="changeLocale('ko')">한글</button>&nbsp;
        <button @click="changeLocale('en')">영어</button>
      </p>
    </div>
    <router-view></router-view>
    <footer>
      <h2>footer Indicator Bar</h2>
      <ul class="wrap_indicater">
        <li class="menu n01 on">홈</li>
        <li class="menu n02">검색</li>
        <li class="menu n03">레시피박스</li>
        <li class="menu n04">마이페이지</li>
      </ul>
    </footer>
  </div>
</template>

<script>
import i18n from "@/i18n";
import '@/assets/css/common.css';
import '@/assets/css/eu.css';
import { mapGetters, mapActions } from "vuex";

export default {
  methods: {
    changeLocale(locale) {
      if (locale === "ko") {
        i18n.locale = "ko";
      } else if (locale === "en") {
        i18n.locale = "en";
      }
    },
  },
};
</script>

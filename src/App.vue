<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/write" @click.native="checkToken">{{
        $t("title.writeRecipe")
      }}</router-link>
      | <router-link to="/recipedetail/26">{{ $t("title.viewRecipe") }}</router-link> |
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
  </div>
</template>

<script>
import i18n from "@/i18n";
import '@/assets/css/common.css';
import '@/assets/css/eu.css';
import { mapGetters, mapActions } from "vuex";

export default {
  computed: {
    ...mapGetters("user", ["hasToken"]),
  },

  methods: {
    checkToken() {
      // 현재 토큰이 있는지 확인 .
      if (!this.hasToken) {
        // 토큰이 없을 때 로그인 페이지로 이동.
        this.$router.push({ path: "/login" });
      }
    },

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

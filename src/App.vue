<template>
  <div id="app" class="wrap">
    <!-- <div>
      <p>
        <button @click="changeLocale('ko')">한글</button>&nbsp;
        <button @click="changeLocale('en')">영어</button>
      </p>
    </div> -->
    <router-view></router-view>
    <footer>
      <h2>footer Indicator Bar</h2>
      <ul class="wrap_indicater">
        <router-link class="link" to="/"><li class="menu n01" :class="{ on : this.$router.currentRoute.path=='/'}">{{ $t("footermenu.home") }}</li></router-link>
        <li class="menu n02">{{ $t("footermenu.search") }}</li>
        <router-link class="link" to="/recipeboxlist/47"><li class="menu n03" :class="{ on : this.$router.currentRoute.path=='/recipeboxlist/47'}">{{ $t("footermenu.recipebox") }}</li></router-link>
        <!-- <router-link class="link" to="/my"><li class="menu n04">{{ $t("footermenu.my") }}</li></router-link> -->
        <router-link class="link" to="/write"><li class="menu n04" :class="{ on : this.$router.currentRoute.path=='/write'}">{{ $t("title.writeRecipe") }}</li></router-link>
      </ul>
    </footer>
  </div>
</template>

<script>
import i18n from "@/i18n";
import "@/assets/css/eu.css";
import "@/assets/css/common.css";

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

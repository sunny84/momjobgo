<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/write">{{ $t("title.writeRecipe")}}</router-link> |
      <router-link to="/recipebox">{{ $t("title.recipeBox")}}</router-link> |
      <router-link to="/login">login</router-link>  |
      <router-link to="/about">About</router-link>  |
      <router-link to="/filter">{{ $t("title.RecipeFilter")}}</router-link>  |
      <router-link to="/loginkko">loginkko</router-link>  
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
import i18n from '@/i18n'

export default {

  methods: {
    changeLocale(locale) {
      if(locale === 'ko'){
        i18n.locale = 'ko';
      } else if(locale === 'en'){
        i18n.locale = 'en';
      }
    }
  },
}
</script>


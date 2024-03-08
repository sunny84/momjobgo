<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <h4>{{ $t('app.name') }}-맘잡고모의프로젝트</h4>


    <p>{{ $t("title.writeRecipe")}}</p>
    <p>{{ $t("description.writeRecipe")}}</p>



    <div>commit 테스트</div>

    



  </div>
</template>

<script>
export default {
  name: 'HomeView',
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

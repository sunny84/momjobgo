<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <h4>{{ $t('app.name') }}-맘잡고모의프로젝트</h4>

    <!-- 언어변경 테스트를 위한 코드 -->
    <p>
        <button @click="changeLocale('ko')">한글</button>&nbsp;
        <button @click="changeLocale('en')">영어</button>
    </p>

    <p>{{ $t("title.writeRecipe")}}</p>
    <p>{{ $t("description.writeRecipe")}}</p>



    <div>commit 테스트</div>

    



  </div>
</template>

<script>
import i18n from '@/i18n'

export default {
  name: 'HomeView',
  components: {
  },
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

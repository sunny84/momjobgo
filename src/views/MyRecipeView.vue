<template>
    <!--HEADER-->
    <!--CONTENT-->
    <div class="contents">
        <!--{{$t("content.myRecipe")}} {{ 나의 레시피 수 }}-->
        <h4>{{$t("content.myRecipe")}} #</h4>
        <button>{{$t("button.write")}}</button>
        <!--
            레시피 목록 기본 노출 3개
            <a herf="레시피 상세페이지로 이동">{{img}}{{title}}{{sub_title}}{{time}}{{start}}{{?}}</a>
            <button>레시피 박스 저장</button>
        -->
    </div>
    <!--FOOTER-->
</template>

<script>

export default {
    name : "MyRecipeView",
    data: ()=>({

    }),

    methods : {

    },
}
</script>
<style>
</style>

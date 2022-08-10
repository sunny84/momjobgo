<template>
  <main class="recipewrite">
    <!-- 공개여부(open) 값에 따라서 미리보기(false)/상세보기(true)로 구분 -->
    <div class="row">
      <h1 class="fl" v-if="recipe_data.open === true">
        <img src="@/assets/images/icon_back.png" alt="돌아가기" title="돌아가기" style="cursor: pointer;" @click="$router.back">
        <span class="color-orange padding-left-15">{{ $t("title.viewRecipe") }}</span>
      </h1>
      <h1 class="fl" v-else>
        <img src="@/assets/images/icon_back.png" alt="돌아가기" title="돌아가기" style="cursor: pointer;">
        <span class="color-orange padding-left-15">{{ $t("title.previewRecipe") }}</span>
      </h1>
    </div>
    <!-- <div class="btn btn-default edit fr margin-right-5"><span class="padding-right-5"> 편집 </span></div> -->

    <!-- 작성자 정보 -->
    <div class="ph_st_wrap margin-top-10">
      <!-- TODO : profile_img 구현된 후 적용할 것 -->
      <!--<div class="fl"><img :src="recipe_data.writer.profile_img" @error="setDefaultProfileImg" class="sphoto"></div>-->
      <div class="fl">
        <img src="@/assets/images/photo_empty.png" class="sphoto" />
      </div>
      <div class="fl name">{{ recipe_data.writer.nickname !== null ? recipe_data.writer.nickname : recipe_data.writer.snsId.substr(0, 5) + "..."}}
        <span class="recipenum">{{ recipe_data.writer.recipeCount }} Recipes</span></div>
      <!-- <div class="fr starnum">4.5</div> -->
    </div>

    <div class="wrap_contents margin-top-10">
      <div class="contents1">
        <!--대표이미지 -->
        <img class="pic" :src="`${$API_SERVER}/file/download?fileId=${recipe_data.mainImgId}`" />
        <div class="text">
          <!-- 제목 / 부제목 -->
          <div class="row">
            <div class="fl" style="width:70%;">
              <div class="title fl">{{ recipe_data.title }}</div>
              <div class="longtext">{{ recipe_data.subTitle }}</div>
            </div>
            <div class="fr right" style="width:30%;">
              <span class="bookmark2"></span>
              <span class="share"></span>
            </div>
          </div>
        </div>

        <!-- 이유시기 / 조리시간 -->
        <div class="wrap_info row border-b-dotted">
          <div class="fl" style="width:30%;"><span class="squre3">{{ $t("option.period[" + recipe_data.period + "]") }}</span></div>
          <div class="fl" style="width:30%; margin-top:3px;"><span class="bullet clock fl" style="width:30% ;">{{ $t("option.timeTaken[" + recipe_data.timeTaken + "]") }}</span></div>
          <!-- <div class="fl" style="width:30%; margin-top:3px;"><span class="bullet star fl" style="width:20% ;">5.0</span></div> -->
        </div>
      </div>
    </div>

    <!-- 재료소개 -->
    <h2>
      <span class="title3">{{ $t("content.listOfIngredient") }}</span>
      <span class="ti_pic1">주방장</span>
      <img src="@/assets/images/bul_info.png">
      <img src="@/assets/images/img_infobox.png">
    </h2>
    <!--  Q. 분량 : (분량 숫자) (몇 회분인지) 표시는 어떻게?
      <p>
      {{ $t("content.quantity") }} : {{ recipe_data.quantity }} {{ $t("content.times") }}
    </p> -->

    <!-- Q. 스푼의 의미는?-->
    <!-- <li class="dot_green">쌀 <span>60g</span>(<span class="icon_spoon fs11">X3</span>) </li> -->
    <ul class="wrap_ingred border-b-dotted">
      <li class="dot_green" v-for="(ing, idx) in recipe_data.ingredients" :key="idx">
        <span> {{ $t("ingredient." + ing.key) }}  {{ ing.volume }} </span>
        <span v-if="ing.key !== 'WATER'">g</span>
        <span v-else>ml</span>
      </li>
    </ul>

    <!-- 조리순서 -->
    <h2>
      <span class="title3">{{ $t("content.cookingOrder") }}</span>
      <span class="ti_pic2"></span>
    </h2>
    <div class="padding-top-20"  v-for="(order, idx) in recipe_data.cookingOrder" :key="idx">
      <div class="row padding-bottom-10">
          <span class="circleNum1">{{ order.contentsNo }}</span>
          <span> {{ order.contents }}</span>
      </div>
      <img class="pic2 margin-bottom-20" :src="`${$API_SERVER}/file/download?fileId=${order.imgId}`">
    </div>

    <!-- Tip -->
    <ul class="wrap_tip border_green">
      <h2><span class="title3">{{ $t("content.tip") }}</span></h2>
      <li class="star" v-for="(tip, idx) in recipe_data.tips" :key="idx">{{ tip.text }}</li>
    </ul>

    <!-- clip movie -->
    <!-- clip url이 정상적인 youtube url인지 확인/
          - 정상 : iframe으로  display
          - 비정상 : broken movive image로 display -->
    <div class="padding-top-20 padding-bottom-20"  v-if="recipe_data.clipLink">
      <h2 class="padding-bottom-10"><span class="title3 no-border">{{ $t("content.movieClipView") }}</span>
      <span class="ti_pic3">비디오</span></h2>
      <div class="pic2">
        <iframe id="clip" style="width:100%;height:315px !important;" :src="recipe_data.clipLink"></iframe>
      </div>
    </div>

    <!-- youtube link -->
    <div class="btn_green margin-bottom-10" @click="redirectYoutube">
      <span class="icon-go">{{ $t('content.youtubeView')}}</span>
    </div>

    <!-- 이용후기 -->
    <!-- 구현이 된다면.... -->
    <!--
    <h2 class="fl border-b-dotted width100 margin-bottom-20"><span class="title3">이용후기</span><span class="fs14 color-grey2 margin-left-10 no-b">Q&A</span><div class="fr icon-go-gray fs14 margin-top-10 no-b">전체보기</div></h2>
    <div class="wrap_btn clear-both padding-bottom-20">
        <div class="btn_o btn_review"><img src="./images/icon_review_w.png"> 리뷰 작성하기</div>
    </div>

    <div class="ph_st_wrap margin-bottom-20 no-bg" style="padding: 15px 0;width:calc(100% - 5px);">
        <div class="padding-bottom-10 overflow-h">
            <div class="fl"><img src="./images/photo-woman2.png" class="sphoto2" alt="민준맘" title="민준맘 사진"></div>
            <div class="fl name padding-top-5">mohe_21</div>
            <div class="wrap_info fr"><span class="bullet star">5.0</span></div>
        </div>
        <div class="border-b-dotted clear-both"></div>

        <div class="padding10 overflow-h">
            <div class="fl width20 padding-right-5"><img src="./images/sample_pic13.png" class="pic3"></div>
            <div class="fl width70" style="line-height:1.2em">쇠고기도 달고 단호박도 달아서 아기가 잘먹는듯해요.
                더 많이 만들어 놔야겠어요!</div>
        </div>
        <div class="wrap_info fr" style="margin-top:-30px;">
            <span class="bullet heart color-grey2">234</span><span class="bullet chat2 color-grey2">reply </span>
        </div>
    </div>

    <div class="ph_st_wrap margin-bottom-40 no-bg" style="padding: 15px 0;width:calc(100% - 5px);">
        <div class="padding-bottom-10 overflow-h">
            <div class="fl"><img src="./images/photo-woman.jpg" class="sphoto2" alt="민준맘" title="민준맘 사진"></div>
            <div class="fl name padding-top-5">hoduya</div>
            <div class="wrap_info fr"><span class="bullet star">5.0</span></div>
        </div>
        <div class="border-b-dotted clear-both"></div>

        <div class="padding10 overflow-h">
            <div class="fl width20 padding-right-5"><img src="./images/sample_pic3.png" class="pic3"></div>
            <div class="fl width70" style="line-height:1.2em">아기가 잘먹고 응가도 잘해서 얼마나
                다행인지요! 변비때문에 너무
                고생이었는데 단호박이 딱이네요.</div>
        </div>
        <div class="wrap_info fr" style="margin-top:-30px;">
            <span class="bullet heart color-grey2">435</span><span class="bullet chat2 color-grey2">reply </span>
        </div>
    </div>-->

    <!-- 밀어서 공개하기 -->
    <div v-if="recipe_data.open === false && recipe_data.writer.id === this.id">
      {{ $t("description.publishRecipe") }}<br />
      <button @click="updateOpen">{{ $t("button.slideAndPublish") }}</button>
    </div>
  </main>
</template>

<script>
import { mapGetters } from "vuex";
//import defaultProfileImg from "@/asset/images/photo_empty.png";

export default {
  name: "RecipeDetailView",
  data: () => ({
    defaultProfileImg : "/assets/images/photo_empty.png",
    recipeId: 0,
    recipe_data: [],
  }),

  computed: {
    ...mapGetters("user", ["id"]),
  },

  created() {
    this.recipeId = this.$route.params.recipeId;
    this.getRecipeById(this.recipeId);
  },

  methods: {
    async getRecipeById(id) {
      const response = await this.$api(
        `${this.$API_SERVER}/Recipe/detail/${id}`,
        "get"
      ).then((res) => {
        if (res.status === this.HTTP_OK) {
          this.recipe_data = res.data;
        }
      });
    },
    async updateOpen() {
      await this.$api(
        `${this.$API_SERVER}/api/Recipe/updateOpen/${this.recipeId}`,
        "patch"
      ).then((res) => {
        if (res.status === this.HTTP_OK) {
          console.log("레시피 공개 완료");
          this.$router.push("/recipedetail/" + recipeId);
        }
      });
    },
    redirectYoutube() {
      console.log(this.recipe_data.youtubeLink);
      window.open(this.recipe_data.youtubeLink, '_blank', 'location=no, status=no, scrollbars=yes');
    },
    setDefaultProfileImg(e) {
      console.log("profile error");
      e.target.src = this.defaultProfileImg;
    },
    logmake(str) {
      console.log(str);
    },
  },
};
</script>

<style></style>

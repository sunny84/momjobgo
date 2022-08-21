<template>
  <main class="recipewrite">
    <!-- 공개여부(open) 값에 따라서 미리보기(false)/상세보기(true)로 구분 -->
    <div class="row">
      <h1 class="fl" v-if="recipe_data.open === true">
        <img src="@/assets/images/icon_back.png" alt="돌아가기" title="돌아가기" style="cursor: pointer;" @click="$router.go(-1)">
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
      <div class="fl" :class="[recipe_data.writer.profileImg !='' ? 'dp-inline-block' : 'dp-none']">
        <img :src="recipe_data.writer.profileImg" class="sphoto"></div>
      <div class="fl" :class="[recipe_data.writer.profileImg !='' ? 'dp-none' : 'dp-inline-block']">
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
            <!-- 로그인 되어 있으면 1. 구독여부 표시하고 2. 구독누르면 작동 -->
            <div  v-if="recipe_data.open === true" class="fr right" style="width:30%;">
              <span class="bookmark2" @click="callRecipeBox(recipeId)"></span>
              <span class="share"></span>
            </div>
          </div>
        </div>

        <!-- 이유시기 / 조리시간 -->
        <div class="wrap_info row border-b-dotted">
          <div class="fl" style="width:30%;"><span class="squre3">{{ $t("option.period[" + recipe_data.period + "]")[0] }}</span></div>
          <div class="fl" style="width:30%; margin-top:3px;"><span class="bullet clock fl" style="width:30% ;">{{ $t("option.timeTaken[" + recipe_data.timeTaken + "]") }}</span></div>
          <!-- TODO : 별점 기능(flow 3이후) -->
          <!-- <div class="fl" style="width:30%; margin-top:3px;"><span class="bullet star fl" style="width:20% ;">5.0</span></div> -->
        </div>
      </div>
    </div>

    <!-- 재료소개 -->
    <h2 class="fl">
      <span class="title3">{{ $t("content.listOfIngredient") }}</span>
      <span class="ti_pic1">주방장</span>
    </h2>
    <div class="fl">
      <img src="@/assets/images/bul_info.png" class="dp-line-block" @click="popup">
      <span style="box-shadow:1px 2px 3px #ccc; border:1px #f1f1f1 solid; padding:0 14px 0 4px;position: relative;"
        :class="[measuringUnit.isOpened ? 'dp-inline-block' :'dp-none']">
        <span class="icon_spoon fs11">=20</span>
        <img src="@/assets/images/btn_cancel.png" style="position:absolute;right:2px;top:2px;" @click="closePopup"><!-- 계량단위 -->
      </span>
      <span class="fs11 dp-block">{{$t("content.total")}} {{recipe_data.quantity}} 
        {{ $t("content.times")}} (1{{ $t("content.times")}} 150g)</span>
    </div>
    <ul class="wrap_ingred border-b-dotted clear-both">
      <li class="dot_green" v-for="(ing, idx) in recipe_data.ingredients" :key="idx">
        <span> {{ $t("ingredient." + ing.key) }}  {{ ing.volume }} </span>
        <span v-if="ing.key !== 'WATER'">g</span>
        <span v-else>ml</span>
        <span v-if="ing.key=='GLUTINOUS_RICE'"> ( <span class="icon_spoon fs11">X{{Math.round(ing.volume*10)/200}}</span>)</span> 
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
    <ul class="wrap_tip border_green" v-if="recipe_data.tips && recipe_data.tips.length!==0">
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
    <div class="btn_green margin-bottom-10 margin-top-20" @click="redirectYoutube" v-if="recipe_data.youtubeLink">
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
    <div v-if="recipe_data.open === false && recipe_data.writer.id === this.id" 
      class="bg-light margin-bottom-40 center no-border">
      <p class="fs20 b color-light-grey margin-bottom-30">{{ $t("description.publishRecipe") }}</p>
      <label class="switch" style="height:30px;">
        <input type="checkbox" />
        <span class="slider round" @click="updateOpen"></span>
      </label>
    </div>

  </main>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "RecipeDetailView",

  data: () => ({
    defaultProfileImg : "/assets/images/photo_empty.png",
    recipeId: 0,
    recipe_data: [],
    measuringUnit:{
      isOpened : false,
      timer: 0
    },
    defaultRecipeBox : [],
    content : "밀어서 공개하기",
  }),

  created() {
    this.recipeId = this.$route.params.recipeId;
    this.getRecipeById(this.recipeId);
    this.getDefaultBoxId();
  },

  computed: {
    ...mapGetters("user", ["id"]),
  },

  methods: {
    async getRecipeById(id) {
      const response = await this.$api(`${this.$API_SERVER}/Recipe/detail/${id}`, "get");
      if(response.status == this.HTTP_OK) {
        this.recipe_data = response.data;
      }
    },
    async updateOpen() {
      // console.log("update success!");
      const response = await this.$api(`${this.$API_SERVER}/api/Recipe/updateOpen/${this.recipeId}`, "patch");
      if (response.status === this.HTTP_OK) {
        console.log("레시피 공개 완료");
        //this.$router.push("/recipedetail/" + recipeId);
        this.$router.push("/");
      }
    },
    async getDefaultBoxId() {
      const response = await this.$api(
      `${this.$API_SERVER}/api/recipebox/default`,
      "get"
      );
      if (response.status === this.HTTP_OK || response.status === this.HTTP_CREATED) {
          this.defaultRecipeBox = response.data;
      }
      if(response.data == ''){
        console.log("create Default Box!")
        const response = await this.$api(
        `${this.$API_SERVER}/api/recipebox/default`,
        "post",
        {}
        );
        if (response.status === this.HTTP_OK || response.status === this.HTTP_CREATED) {
            this.defaultRecipeBox = response.data;
        }
      }
    },
    async callRecipeBox(id) {
      // 이미 담긴 레시피인지 확인
      const res = await this.$api(
      `${this.$API_SERVER}/api/reciperecipebox/recipeId`,
      "get",
      {id : id}
      );
      if (res.status === this.HTTP_OK) {
        console.log(res.data)
        // 담긴 레시피 정보가 없으면 default 그룹 박스에 담기
        if(res.data.recipe == null){
          const response = await this.$api(
          `${this.$API_SERVER}/api/reciperecipebox`,
          "post",
          {
            box : this.defaultRecipeBox.id,
            recipe : id
          }
          );
          if (response.status === this.HTTP_OK || response.status === this.HTTP_CREATED) {
            console.log("기본박스에 저장 성공")
          }
        } else {
          console.log("이미 담긴 레시피 입니다.")
        }
        location.href=`/recipeboxlist/${id}`;
      }
    },
    callWrite() {
      console.log("write button");
      location.href=`/write`;
    },
    setEmptyImg(e) {
      e.target.src=emptyImg;
    },
    async updateRecipeBox(name) {
      console.log("updateRecipeBox : "+name);
      const response = await this.$api(
      `${this.$API_SERVER}/api/recipebox/${this.defaultRecipeBox.id}`,
      "patch",
      {name: name}
      );
      if (response.status === this.HTTP_CREATED) {
        console.log("이름 변경 성공")
      }
    },
    redirectYoutube() {
      console.log(this.recipe_data.youtubeLink);
      let youtubeAddress = this.recipe_data.youtubeLink;
      if(youtubeAddress.indexOf("https://")<0
        &&youtubeAddress.indexOf("http://")<0) {
          youtubeAddress = "https://"+youtubeAddress;
      }
      window.open(youtubeAddress, '_blank', 'location=no, status=no, scrollbars=yes');
    },
    setDefaultProfileImg(e) {
      console.log("profile error");
      e.target.src = this.defaultProfileImg;
    },
    popup() {
      this.measuringUnit.isOpened =true;
      this.measuringUnit.timer = setTimeout(() => {
          this.measuringUnit.isOpened = false;
        }, 3000);
    },
    closePopup() {
      if(this.measuringUnit.timer > 0){
        clearTimeout(this.measuringUnit.timer);
        this.measuringUnit.isOpened=false;
        this.measuringUnit.timer=0;
      }
    },
  },
};
</script>

<style scoped>
.switch {
  position: relative;
  display: inline-block;
  width: 80%;
  height: 34px;
  padding:4px;
}

.switch input { 
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:hover {
  background: #D5EEEE;
}
.slider:before {
  position: absolute;
  content:'밀어서 공개하기';
  padding-top:1px;
  vertical-align: middle;
  font-weight: 700;
  height: 30px;
  width: 50%;
  top:4px;
  left: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: #028870;
}

input:focus + .slider {
  box-shadow: 0 0 1px #028870;
}

input:checked + .slider:before {
  -webkit-transform: translateX(97%);
  -ms-transform: translateX(97%);
  transform: translateX(97%);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 14px;
}
</style>

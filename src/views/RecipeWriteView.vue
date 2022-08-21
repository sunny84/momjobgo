<template>
  <main class="recipewrite">
    <div class="row">
      <h1 class="fl">
        <img src="@/assets/images/icon_back.png" alt="돌아가기" title="돌아가기">
        <span class="color-orange padding-left-15">{{ $t("title.writeRecipe") }}</span>
      </h1>
      <div class="btn_close fr" @click="reset">닫기</div>
    </div>

    <div class="wrap_menu">
      <ul>
        <li class="menu" :class="{ on : step ===0}" style="cursor: pointer;">1</li>
        <li class="menu" :class="{ on : step ===1}" style="cursor: pointer;">2</li>
        <li class="menu" :class="{ on : step ===2}" style="cursor: pointer;">3</li>
        <li class="menu" :class="{ on : step ===3}" style="cursor: pointer;">4</li>
      </ul>
    </div>

    <!-- Step 1 : 이유 단계, 소요시간 선택 -->
    <div v-show="step === 0">
      <p class="fs20 b">{{ $t("description.writeRecipe") }}</p>

      <div class="wrap_write">
        <h2 class="title">{{ $t("content.step") }}</h2>

        <select  class="width100 padding10" v-model="period">
          <option v-for="(period, idx) in $t('option.period')" :value="idx" :key="idx">
            {{ period[0] }} ( {{ period[1] }} )
          </option>
        </select>

        <h2 class="title">{{ $t("content.quantity") }}</h2>
        <div class="amount">
            <button class="btn_noRadius btn-default subtract" @click="changeQuantity('-')">-</button>
            <input class="intext" type="number" min="1" v-model="quantity">
            <button class="btn_noRadius btn-default add" @click="changeQuantity('+')">+</button>
            <span>{{ $t("content.times") }}</span>
        </div>

        <h2 class="title">{{ $t("content.timeTaken") }}</h2>
        <div class="wrap_time">
          <VueSlideBar v-model="slider.value"
            :data="slider.data" 
            :range="slider.range"
            :lineHeight="10"
            :processStyle="{ backgroundColor: '#028870' }">
            <template slot="tooltip">
              <span class="circle" style="position:absolute ;left:0;top:14px;"></span>
            </template>
          </VueSlideBar>
        </div>
      </div>
    </div>

    <!-- Step 2 : 식재료 선택 -->
    <div v-show="step === 1">
      <p class="fs20 b">{{ $t("description.ingredient") }}</p>
      <div class="wrap_write">
        <h2 class="title">{{ $t("content.choiceOfIngredient") }}</h2>

        <div v-for="(category, idx) in ing_data" :key="idx" v-show="category.id !== 6">
          <h2 v-show="category.id !==1">
            <span class="title" style="color:#000000;">{{ $t("ing_category." + [category.key])[0] }}</span>
            <span class="fs12 color-grey2">{{ $t("ing_category." + [category.key])[1] }}</span>
            <span>&nbsp;</span>
          </h2>
          <div class="wrap_select">
            <span
              v-for="(item, idx) in category.ingredientEntities" :key="idx"
              class="food"
              :class="{on : selectedIngredients.some(e => e.ingredientId===item.id)}"
              @click="setSelectedIng(item)">{{$t('ingredient.' + [item.key])}}
            </span>
          </div>
        </div>
      </div>
    </div>

    <!-- Step 3 : 제목(부제목), 대표사진, 재료양, 조리순서, Tip, 영상url 입력 -->
    <div v-show="step === 2">
      <p class="fs20 b">{{ $t("description.almostDone") }}</p>
      <div class="wrap_write">
        <!-- Title / subtitle -->
        <h2 class="title">{{ $t("content.title")}}</h2>
        <div>
          <input type="text" class="intext margin-bottom-10" style="width:80%;text-align:left;"
            :placeholder="$t('content_description.title')" v-model="title" /> <span> {{ $t("option.period[" + this.period + "]")[1] }}</span>
          <input type="text" class="intext" style="width:95%;"
            :placeholder="$t('content_description.subTitle')" v-model="subTitle" />
        </div>

        <!-- main picture -->
        <h2 class="title">{{ $t("content.mainPicture") }}</h2>
        <div style="width:100%;">
          <label for="mainPicture">
            <img style="max-width:100%;max-height:100%;" :class="[ mainPicture !='' ? 'dp-inline-block' : 'dp-none']"
              :src="mainPicture" @error="setEmptyImg"/>
          </label>
          <label for="mainPicture">
            <div style="padding:100px 0;z-index:5;margin:auto;text-align:center;background-color:#d9d9d9;" :class="[ mainPicture !='' ? 'dp-none' : 'dp-block']">
              <img src="@/assets/images/btn_addphoto.png" />
              <span class="dp-block color-grey2">ADD PHOTO</span>
            </div>
          </label>
          <input type="file" id="mainPicture" class="dp-none" accept="image/*" @change="uploadMainImg" />
        </div>

        <!-- list and volume of ingredient -->
        <h2 class="title" style="border-bottom: 0;">
          <span style="border-bottom: 3px #ccc solid;">{{ $t("content.listOfIngredient") }}</span>
        <!--<span>{{ $t("content_description.measuringUnit") }}</span>-->
          <span class="fs11 dp-inline-block" style="border-bottom: 0;padding-left:10px;">{{$t("content.total")}}{{this.quantity}}{{$t("content.times")}}
          (1{{$t("content.times")}} 150g)</span>
        </h2>

        <div v-for="(item, idx) in selectedIngredients" :key="idx">
          <label :for="item.key"><span class="dp-inline-block" style="width:70px;">{{ $t("ingredient." + [item.key]) }}</span></label>
          <input type="number" :id="item.key" v-model="item.volume" min="1" class="intext margin-bottom-10" style="width:70%;" />
          <span v-if="item.ingredientId !== 1"> g</span>
          <span v-else> ml</span>
        </div>

        <!-- cooking order -->
        <h2 class="title">{{ $t("content.cookingOrder") }}</h2>
        <draggable v-model="cookingOrder" handle=".handler" :animation="400">
          <div class="margin-bottom-10 overflow-h" v-for="(order, ord_idx) in cookingOrder" :key="`o-${ord_idx}`">
              <div class="circleNum">{{ ord_idx+1 }}</div>
              <textarea type="text" class="textarea" :placeholder="$t('content_description.cookingOrder')" v-model="order.contents"></textarea>
              <input :id="`cookingPicture${ord_idx}`" type="file" accept="image/*" @change="uploadOrderImg(order, $event)" class="dp-none" />
              <label :for="`cookingPicture${ord_idx}`">
                <img :src="order.imgUrl" style="width:80px;height:80px;vertical-align:top;"
                  :class="[ order.imgUrl !='' ? 'dp-inline-block' : 'dp-none']" />
                <div class="btn_noRadius addphoto margin-right-2" style="padding:0;z-index:5;"
                  :class="[ order.imgUrl !='' ? 'dp-none' : 'dp-inline-block']" >+</div>
              </label>
              <div class="handler btn_noRadius btn-default move dp-inline-block" style="padding:0;">+</div>
          </div>
        </draggable>
        <div class="btn_circle_plus" @click="addOrder"></div>

        <!-- Tip -->
        <h2 class="title">{{ $t("content.tip") }}
          <!-- <span class="title">({{ $t("content_description.option") }})</span> -->
        </h2>
        <div class="margin-bottom-10" style="position:relative;" v-for="(tip, idx) in Tips" :key="`t-${idx}`">
            <div class="circleNum">{{ idx+1 }}</div>
            <input type="text" class="intext" style="width:89%;" :value="tip.text" @input="updateTip(idx, $event)" :placeholder="$t('content_description.tip')">
            <button class="btn_noRadius fr addTip" @click="addTip" type="button">+</button>
        </div>

        <!-- Clip moive link -->
        <h2><span class="fs14">{{ $t("content.movieClipUrl") }}</span></h2>
        <input type="url" class="intext margin-bottom-15" style="width:92%;"
          :placeholder="$t('content_description.movieClipUrl')" pattern="https://.*" v-model="clipUrl">

        <!-- Youtube Full link -->
        <h2><span class="fs14">{{ $t("content.youtubeUrl") }}</span></h2>
        <input type="text" class="intext margin-bottom-20" style="width:92%;"
         :placeholder="$t('content_description.youtubeUrl')" pattern="https://.*" v-model="youtubeUrl">
      </div>
    </div>

    <!--Step 4 : 저장 전 미리보기-->
    <div v-show="step === 3">
      <p class="fs20 b">{{ $t("description.finishAndSave") }}</p>
      <div class="wrap_contents margin-top-10">
        <div class="contents1">
         <img class="pic" :src="mainPicture">
         <div class="text">
            <div class="row">
                <div class="fl" style="width:70%;">
                    <div class="title fl">{{ title }}</div>
                    <div class="longtext fl">{{ subTitle }}</div>
                </div>
                <!-- TODO : 북마크 /공유 (flow 3 이후)-->
                <div class="fr right" style="width:30%;">
                    <span class="bookmark2"></span>
                    <span class="share"></span>
                </div>
            </div>
         </div>
         <div class="wrap_info row border-b-dotted">
            <div class="fl" style="width:30%;"><span class="squre3">{{$t("option.period["+period+"]")[0]}}</span></div>
            <div class="fl" style="width:30%; margin-top:3px;"><span class="bullet clock fl" style="width:30% ;">{{ $t("option.timeTaken[" + timeTaken + "]") }}</span></div>
            <!-- TODO : 별점기능 (flow 3 이후)-->
            <!-- <div class="fl" style="width:30%; margin-top:3px;"><span class="bullet star fl" style="width:20% ;">5.0</span></div> -->
         </div>
        </div>
       </div>
    </div>
    <div class="wrap_write" style="margin-top:0px;">
        <div class="exp">{{ $t("description.autoSave") }}</div>
        <div>
          <div class="btn-pre fl margin-bottom-40" v-show="this.step !== 0" @click="validateByStep(-1)">{{ $t("button.back") }}</div>
          <div class="btn-next fr " v-show="this.step !== 3" @click="validateByStep(+1)">{{ $t("button.next") }}</div>
          <div class="btn-next fr" v-show="this.step === 3" @click="publish">{{ $t("button.saveAndPreview") }}</div>
        </div>
    </div>

  </main>
</template>

<script>
import emptyImg from "@/assets/emptyImg.png";
import VueSlideBar from "vue-slide-bar";
import { mapGetters } from "vuex";
import draggable from "vuedraggable";

export default {
  name: "RecipeWriteView",
  data: () => ({
    // for real
    ing_data: [], // for real code
    step: 0, // 레시피 작성 과정 단계
    period: 0, // 이유 시기
    quantity: 1, // 몇 회분
    timeTaken: 0, // 소요시간
    selectedIngredients: [], // 선택된 재료와 양
    title: "", // 레시피 제목
    subTitle: "", // 레시피 부제목
    mainPicture: "",
    mainImg: "", // 대표사진
    cookingOrder: [{ contents: "", imgUrl: "", fileData: "" }], // 조리 순서
    cookingOrderData: [], // 서버로 보낼 cooking order data
    Tips: [{ orderNum: 1, text: "" }], // Tip
    youtubeUrl: "",
    clipUrl: "",
    slider: {
      value: -1,
      data: [],
      range: [],
    },
  }),

  components : {
    VueSlideBar,
    draggable,
  },
  computed: {
    ...mapGetters("user", ["id"]),
  },

  watch: {
      quantity(a) {
        if(isNaN(Number(a)) || a<1) {
          this.quantity=1;
        }
      }
  },
  created() {
    this.callIngredientCategory();
    //this.initWriteRecipeProcess();
    this.initSlideBar();
  },

  mounted() {
    this.$checkToken("write");
  },
  methods: {
    initWriteRecipeProcess() {
      this.step = 0;
      this.period = 0;
      this.quantity = 1;
      this.timeTaken = 0;
      this.selectedIngredients = [];
      this.title = "";
      this.subTitle = "";
      this.mainPicture = "";
      this.mainImg = "";
      this.cookingOrder = [{ contents: "", imgUrl: "", fileData: "" }];
      this.cookingOrderData = [];
      this.Tips = [{ orderNum: 1, text: "" }];
      this.youtubeUrl = "";
      this.clipUrl = "";
    },
    initSlideBar() {
      for(let i = 0; i < this.$i18n.t("option.timeTaken").length; i++){
        this.slider.data.push(i);
        this.slider.range.push({label: this.$i18n.t(`option.timeTaken[${i}]`)})
      }
      this.slider.value = this.timeTaken;
    },
    reset() {
      if (confirm("작성을 취소하시겠습니까?")) {
        this.$router.push("/");
        this.initWriteRecipeProcess();
      }
    },
    async callIngredientCategory() {
      await this.$api(`${this.$API_SERVER}/Ingredient/join/category`, "get").then(
        (res) => {
          if (res.status === this.HTTP_OK) {
            this.ing_data = res.data;
          } else {
            console.log("NOT Ok");
          }
        }
      );
    },
    validateByStep(arrow) {
      // validate data by step then go next/back
      // push "WATER" for essential ingredient on step 1before go to step 2
      if (arrow > 0 && this.step === 0 && !this.checkQuantity()) {
        return;
      } else if (arrow > 0 && this.step === 1) {
        this.selectedIngredients.push({ ingredientId: 1, key: "WATER", volume: "" });
      } else if (arrow < 0 && this.step === 2) {
        this.selectedIngredients.pop();
      } else if (arrow > 0 && this.step === 2) {
        if (
          !this.checkTitles() ||
          !this.checkMainImage() ||
          !this.checkIngredients() ||
          !this.checkCookingOrder() ||
          !this.checkMovieUrl()
        ) {
          return;
        }
      }

      // then go
      this.changeStep(arrow);
    },
    changeStep(arrow) {
      this.step = this.step + arrow;
      //this.step=+arrow;
      window.scrollTo(0, 0);
    },
    changeQuantity(arrow) {
      if(arrow==='+') {
        this.quantity++;
      } else if (arrow==='-' && this.quantity>1) {
        this.quantity--;
      }
    },
    setEmptyImg(e) {
      e.target.src = emptyImg;
    },
    setSelectedIng(item) {
      let index = this.selectedIngredients.findIndex(x => x.ingredientId === item.id);
      if(index>=0) {
        this.selectedIngredients.splice(index, 1);
      }else{
        this.selectedIngredients.push({
          ingredientId: item.id,
          key: item.key,
          volume: '' });
      }
    },

    uploadMainImg(e) {
      let files = e.target.files;
      if (this.checkFileType(files[0].type, "image/")) {
        this.mainPicture = URL.createObjectURL(files[0]);
        this.mainImg = files[0];
        //console.log(this.mainImg);
      } else {
        alert("이미지 파일을 선택하셔야 합니다");
        this.setEmptyImg(e);
      }
    },
    uploadOrderImg(targetOrder, e) {
      let files = e.target.files;

      //console.log(targetOrder);
      if (this.checkFileType(files[0].type, "image/")) {
        // Have to use wrapping method for array due to detecting changed array of VUE
        targetOrder.imgUrl = URL.createObjectURL(files[0]);
        targetOrder.fileData = files[0];
      } else {
        alert("이미지 파일을 선택하셔야 합니다");
        this.setEmptyImg(e);
      }
    },
    addOrder() {
      this.cookingOrder.push({
        contents: "",
        imgUrl: "",
        fileData: "",
      });

      this.fileCnt++;
    },
    addTip() {
      if (this.Tips.length < 5) {
        this.Tips.push({ orderNum: this.Tips.length + 1, text: "" });
      }
    },
    updateTip(idx, e) {
      this.Tips[idx].text = e.target.value;
    },
    checkQuantity() {
      if (this.quantity >= 1) {
        this.quantity = Number(parseInt(this.quantity));
        return true;
      }
      alert("분량은 1보다 커야합니다");
      return false;
    },
    checkTitles() {
      this.title.trim();
      this.subTitle.trim();
      if (this.title.length !== 0 && this.subTitle.length !== 0) {
        return true;
      }
      alert("제목과 부제목을 입력하세요");
      return false;
    },
    checkFileType(inputedType, wantedType) {
      if (!inputedType.indexOf(wantedType)) {
        return true;
      } else {
        return false;
      }
    },
    checkMainImage() {
      if (this.mainImg !== 0 && this.mainImg.name.length !== 0) {
        return true;
      } else {
        alert("대표사진을 입력하세요");
        return false;
      }
    },
    checkIngredients() {
      for (let i = 0; i < this.selectedIngredients.length; i++) {
        if (this.selectedIngredients[i].volume.trim().length === 0) {
          alert("재료양을 입력하세요");
          return false;
        }
      }
      return true;
    },
    checkCookingOrder() {
      // first cooking order should be existed
      if (this.cookingOrder.length > 1) {
        for (let i = 0; i < this.cookingOrder.length; i++) {
          if (
            !(
              (this.cookingOrder[i].fileData.length !== 0 &&
                this.cookingOrder[i].fileData.name.length !== 0) ^
              (this.cookingOrder[i].contents.trim().length === 0)
            )
          ) {
            alert("조리순서 설명과 사진이 필요합니다1");
            return false;
          }
        }
        return true;
      } else {
        if (
          this.cookingOrder[0].fileData.length == 0 ||
          this.cookingOrder[0].fileData.name.length === 0 ||
          this.cookingOrder[0].contents.trim().length === 0
        ) {
          alert("조리순서 설명과 사진이 필요합니다2");
          return false;
        }
        return true;
      }
    },
    checkMovieUrl() {
      if (
        this.clipUrl.trim() != "" &&
        this.clipUrl.indexOf("youtube.com/") < 0 &&
        this.clipUrl.indexOf("youtu.be/") < 0
      ) {
        alert("클립 영상은 유투브 영상만 허용됩니다. 올바른 주소를 입력해주세요");
        return false;
      }
      if (
        this.youtubeUrl.trim() != "" &&
        this.youtubeUrl.indexOf("youtube.com/") < 0 &&
        this.youtubeUrl.indexOf("youtu.be/") < 0
      ) {
        alert("영상은 유투브 영상만 허용됩니다. 올바른 주소를 입력해주세요");
        return false;
      }
      return true;
    },
    calibMainImg() {
      // URL.revokeObjectURL(this.mainPicture);
    },
    calibIngredientsVolume() {
      for (let i = 0; i < this.selectedIngredients.length; i++) {
        this.selectedIngredients[i].volume = Number(this.selectedIngredients[i].volume);
      }
      return true;
    },
    calibCookingOrder() {
      for (let i = 0; i < this.cookingOrder.length; i++) {
        // URL.revokeObjectURL(this.cookingOrder[i].imgUrl);
        // delete this.cookingOrder[i].imgUrl;
        if (
          !this.cookingOrder[i].fileData &&
          !this.cookingOrder[i].contents.trim().length
        ) {
          this.cookingOrder.splice(i, 1);
          // } else {
          //   delete this.cookingOrder[i].fileData;
        }
      }
      for (let i = 0; i < this.cookingOrder.length; i++) {
        this.cookingOrderData.push({
          contentsNo: i+1,
          contents: this.cookingOrder[i].contents,
        });
      }
      console.log(this.cookingOrderData);
    },
    calibTips(tips) {
      for (let i = 0; i < tips.length; i++) {
        tips[i].text.trim();
        if (tips[i].text.trim().length === 0) {
          tips.splice(i, 1);
        }
      }
    },
    calibUrls() {
      // calibrate youtube Url & clip url
      this.youtubeUrl.trim();
      this.clipUrl.trim();
    },
    calibrateAllData() {
      // callibrate all data before save

      // for selected ingredients
      // 입력된 재료양은 숫자로 바꿔주고 재료양이 없으면 기본값을 0으로 입력해주기
      this.calibIngredientsVolume();

      // for main image
      // revoke blob image for preview
      this.calibMainImg();

      // for cooking order
      // 조리순서 이미지url항목지우기
      // 비어있는 조리 순서항목이 있으면 지우기
      this.calibCookingOrder();

      // for Tip, youtube url, clip url
      // 내용이 있으면 trim하고 내용이 없으면 비어 있는 Tip, url, clip url 지우기
      this.calibTips(this.Tips);
      this.calibUrls();
    },
    releaseAndRemoveExtraData() {
      URL.revokeObjectURL(this.mainPicture);
      for (let i = 0; i < this.cookingOrder.length; i++) {
        URL.revokeObjectURL(this.cookingOrder[i].imgUrl);
        // delete this.cookingOrder[i].imgUrl;
        // delete this.cookingOrder[i].fileData;
      }
    },
    makeParams() {
      const params = {
        //for recipe table
        period: this.period,
        quantity: this.quantity,
        timeTakenId: this.timeTaken,

        // for content table
        contentsEntity: {
          category: 1, // content category id for recipe
          writer: this.id, // id of user table
          title: this.title,
          subTitle: this.subTitle,
        },

        recipeIngredientMapEntities: this.selectedIngredients,
        cookingOrderEntities: this.cookingOrderData,
      };

      // Add tips if it's existed
      if (this.Tips.length !== 0) {
        params.tipEntities = this.Tips;
      }

      // Add urls if it's existed
      if (this.youtubeUrl.trim().length !== 0) {
        params.youtubeLink = this.youtubeUrl;
      }

      if (this.clipUrl.trim().length !== 0) {
        params.clipLink = this.clipUrl;
      }

      return params;
    },
    async publish() {
      // make form data for server
      const formData = new FormData();

      formData.append("file", this.mainImg, "M" + this.mainImg.name);
      for (let i = 0; i < this.cookingOrder.length; i++) {
        // change file name
        var filename = this.cookingOrder[i].fileData.name;

        formData.append(
          "file",
          this.cookingOrder[i].fileData,
          "C" +
            filename.substring(0, filename.lastIndexOf(".")) +
            String(i + 1).padStart(2, 0) +
            filename.substring(filename.lastIndexOf("."), filename.length)
        );
      }

      // calibarate all data
      this.calibrateAllData();

      const allParams = this.makeParams();

      // Insert new recipe
      const resContents = await this.$api(
        `${this.$API_SERVER}/api/Recipe/write`,
        "post",
        "",
        allParams
      );
      if (resContents.status == this.HTTP_OK) {
        const contentsId = resContents.data.contentsId;
        const recipeId = resContents.data.recipeId;

        // upload files
        const resFiles = await this.$api(
          `${this.$API_SERVER}/file/upload`,
          "post",
          { contentsId: contentsId },
          formData,
          { "Content-Type": "multipart/form-data" }
        );
        if (resFiles.status == this.HTTP_OK) {
          this.releaseAndRemoveExtraData();
          this.$router.push("/recipedetail/" + recipeId);
          this.initWriteRecipeProcess();
        }
      }
    },
  },
};
</script>

<style scoped>
.pickpicture {
  display: none;
  z-index: 5;
}
</style>

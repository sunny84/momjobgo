<template>
  <div>
    <h4>{{ $t("title.writeRecipe") }}</h4>
    <p align="right" @click="reset()">X</p>
    <p>단계 : {{ step + 1 }}</p>
    <!-- Step 1 : 이유 단계, 소요시간 선택 -->
    <div v-show="step === 0">
      <p>{{ $t("description.writeRecipe") }}</p>
      <p>
        {{ $t("content.step") }}
        <select v-model="period">
          <option v-for="(period, idx) in $t('option.period')" :value="idx" :key="idx">
            {{ period[0] }} ( {{period[1]}} )
          </option>
        </select>
        {{ $t("content.quantity") }}
        <input type="number" min="0" v-model="quantity" id="times" />
        {{ $t("content.times") }}
      </p>
      <p>{{ $t("content.timeTaken") }}</p>
      <p>
        <select v-model="timeTaken">
          <option v-for="(time, idx) in $t('option.timeTaken')" :value="idx" :key="idx">
            {{ time }}
          </option>
        </select>
      </p>
    </div>

    <!-- Step 2 : 식재료 선택 -->
    <div v-show="step === 1">
      <p>{{ $t("description.ingredient") }}</p>
      <p>{{ $t("content.choiceOfIngredient") }}</p>

      <div v-for="(category, idx) in ing_data" :key="idx" v-show="category.id !== 6">
        <p v-show="category.id !== 1">{{ $t("ing_category." + [category.key]) }}</p>
        <p>
          <span v-for="(item, idx) in category.ingredientEntities" :key="item.id">
            <input
              :id="`${item.key}`"
              type="checkbox"
              v-model="selectedIngredients"
              :value="{ ingredientId: item.id, key: item.key, volume: '' }"
            />
            <label :for="`${item.key}`">{{ $t("ingredient." + [item.key]) }}</label>
            <br v-if="category.id === 1 && idx % 3 === 1" />
            <br v-else-if="category.id !== 1 && idx % 3 === 2" />
          </span>
        </p>
      </div>
    </div>

    <!-- Step 3 : 제목(부제목), 대표사진, 재료양, 조리순서, Tip, 영상url 입력 -->
    <div v-show="step === 2">
      <p>{{ $t("description.almostDone") }}</p>
      <!-- title & subtitle -->
      <p><input type="text" :placeholder="$t('content.title')" v-model="title" /></p>
      <p>
        <input type="text" :placeholder="$t('content.subTitle')" v-model="subTitle" />
      </p>
      <!-- main picture -->
      <p>{{ $t("content.mainPicture") }}</p>
      <p><img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg" /></p>
      <p>
        <input
          type="file"
          id="mainPicture"
          class="pickpicture"
          accept="image/*"
          @change="uploadMainImg"
        /><label for="mainPicture">+</label>
      </p>
      <!-- list and volume of ingredient -->
      <p>
        {{ $t("content.listOfIngredient") }} {{ $t("content_description.measuringUnit") }}
      </p>
      <p v-for="(item, idx) in selectedIngredients" :key="idx">
        <label :for="item.key">{{ $t("ingredient." + [item.key]) }} </label>
        <input type="number" :id="item.key" v-model="item.volume" min="1" />
        <span v-if="item.ingredientId !== 1"> g</span>
        <span v-else> ml</span>
      </p>
      <!-- cooking order-->
      <!-- 조리과정 추가하고 전체를 하나의 object로 만들어서 보내는 것 테스트 -->
      <p>{{ $t("content.cookingOrder") }} <span @click="addOrder">+</span></p>
      <p v-for="(order, ord_idx) in cookingOrder" :key="`o-${ord_idx}`">
        <button @click="goUp(ord_idx)">↑</button>
        <button @click="goDown(ord_idx)">↓</button>
        <span>{{ order.contentsNo }}</span>
        <textarea width="140px" height="50" v-model="order.contents"></textarea>
        <img :src="order.imgUrl" width="50px" height="50px" @error="setEmptyImg" />
        <input
          :id="`cookingPicture${ord_idx}`"
          type="file"
          accept="image/*"
          @change="uploadOrderImg(order, $event)"
          class="pickpicture"
        /><label :for="`cookingPicture${ord_idx}`">+</label>
      </p>
      <!-- Tip -->
      <p>
        {{ $t("content.tip") }} ({{ $t("content_description.option") }})
        <span @click="addTip">+</span>
      </p>
      <p v-for="(tip, idx) in Tips" :key="`t-${idx}`">
        <input
          type="text"
          :placeholder="$t('content_description.tip')"
          :value="tip.text"
          @input="updateTip(idx, $event)"
        />
      </p>
      <!-- Clip moive link -->
      <p>{{ $t("content.movieClipUrl") }} ({{ $t("content_description.option") }})</p>
      <p>
        <input
          type="url"
          :placeholder="$t('content_description.movieClipUrl')"
          pattern="https://.*"
          v-model="clipUrl"
        />
      </p>
      <!-- Youtube Full link -->
      <p>{{ $t("content.youtubeUrl") }} ({{ $t("content_description.option") }})</p>
      <p>
        <input
          type="url"
          :placeholder="$t('content_description.youtubeUrl')"
          pattern="https://.*"
          v-model="youtubeUrl"
        />
      </p>
    </div>

    <div v-show="step === 3">
      <p>{{ $t("description.finishAndSave") }}</p>
      <p><img :src="mainPicture" width="200px" height="150px" /></p>
      <p>{{ title }}</p>
      <p>{{ subTitle }}</p>
      <p>{{ $t("option.timeTaken[" + timeTaken + "]") }}</p>
    </div>

    <div>
      <p>
        <button v-show="this.step !== 0" @click="validateByStep(-1)">
          {{ $t("button.back") }}
        </button>
        &nbsp;
        <button v-show="this.step !== 3" @click="validateByStep(+1)">
          {{ $t("button.next") }}
        </button>
        &nbsp;
        <button v-show="this.step === 3" @click="publish">
          {{ $t("button.saveAndPreview") }}
        </button>
      </p>
      <p>{{ $t("description.autoSave") }}</p>
    </div>
  </div>
</template>

<script>
import emptyImg from "@/assets/emptyImg.png";
import { mapGetters } from "vuex";

export default {
  name: "RecipeWriteView",
  data: () => ({
    // for real
    ing_data: [], // for real code
    step: 0, // 레시피 작성 과정 단계
    period: 0, // 이유 시기
    quantity: "", // 몇 회분
    timeTaken: 0, // 소요시간
    selectedIngredients: [], // 선택된 재료와 양
    title: "", // 레시피 제목
    subTitle: "", // 레시피 부제목
    mainPicture: "",
    mainImg: "", // 대표사진
    cookingOrder: [{ contentsNo: 1, contents: "", imgUrl: "", fileData: "" }], // 조리 순서
    Tips: [{ orderNum: 1, text: "" }], // Tip
    youtubeUrl: "",
    clipUrl: "",
  }),

  computed: {
    ...mapGetters("user", ["id"]),
  },

  created() {
    this.callIngredientCategory();
  },

  mounted() {
    this.$checkToken("write");
  },
  methods: {
    initWriteRecipeProcess() {
      this.step = 0;
      this.period = 0;
      this.quantity = "";
      this.timeTaken = 0;
      this.selectedIngredients = [];
      this.title = "";
      this.subTitle = "";
      this.mainPicture = "";
      this.mainImg = "";
      this.cookingOrder = [{ contentsNo: 1, contents: "", imgUrl: "", fileData: "" }];
      this.Tips = [{ orderNum: 1, text: "" }];
      this.youtubeUrl = "";
      this.clipUrl = "";
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
    },
    setEmptyImg(e) {
      e.target.src = emptyImg;
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
        contentsNo: this.cookingOrder.length + 1,
        contents: "",
        imgUrl: "",
        fileData: "",
      });

      this.fileCnt++;
    },
    sortByContentId() {
      this.cookingOrder.sort(function (a, b) {
        return a.contentsNo - b.contentsNo;
      });
    },
    goDown(idx) {
      // go down for cooking order
      if (idx < this.cookingOrder.length - 1) {
        this.cookingOrder[idx].contentsNo = idx + 2;
        this.cookingOrder[idx + 1].contentsNo = idx + 1;
      }
      this.sortByContentId();
    },
    goUp(idx) {
      // go up for cooking order
      if (idx > 0) {
        this.cookingOrder[idx].contentsNo = idx;
        this.cookingOrder[idx - 1].contentsNo = idx + 1;
      }
      this.sortByContentId();
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
      URL.revokeObjectURL(this.mainPicture);
    },
    calibIngredientsVolume() {
      for (let i = 0; i < this.selectedIngredients.length; i++) {
        this.selectedIngredients[i].volume = Number(this.selectedIngredients[i].volume);
      }
      return true;
    },
    calibCookingOrder() {
      for (let i = 0; i < this.cookingOrder.length; i++) {
        URL.revokeObjectURL(this.cookingOrder[i].imgUrl);
        delete this.cookingOrder[i].imgUrl;
        if (
          !this.cookingOrder[i].fileData &&
          !this.cookingOrder[i].contents.trim().length
        ) {
          this.cookingOrder.splice(i, 1);
        } else {
          delete this.cookingOrder[i].fileData;
        }
      }
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
    makeParams() {
      const params = {
        //for recipe table
        period: this.period,
        quantity: this.quantity,
        timeTakenId: this.timeTaken,

        // for content table
        contentsEntity: {
          category: 0, // content category id for recipe
          writer: this.id, // id of user table
          title: this.title,
          subTitle: this.subTitle,
        },

        recipeIngredientMapEntities: this.selectedIngredients,
        cookingOrderEntities: this.cookingOrder,
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

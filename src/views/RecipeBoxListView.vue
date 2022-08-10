<template>
  <div>
    <main class="recipebox">
    <!--HEADER-->
    <h1 class="fl"><router-link :to="'/recipedetail/'+recipeId"><img src="@/assets/images/icon_back.png" alt="돌아가기" title="돌아가기"/></router-link>
      <span class="color-orange padding-left-15">{{ $t("title.recipeBox") }}</span>
    </h1>
    <div class="wrap_menu">
        <ul>
            <li class="menu" style="cursor: ponter;" onclick="location.href='/myrecipe';">{{ $t("menu.myRecipe") }}</li><!--<router-link :to="'/myrecipe'"></router-link>-->
            <li class="menu on" style="cursor: ponter;" onclick="location.href='/recipeboxlist';">{{ $t("menu.savedRecipe") }}</li><!--<router-link :to="'/recipeboxlist'"></router-link>-->
            <li class="menu" style="cursor: ponter;" onclick="location.href='';">{{ $t("menu.historyRecipe") }}</li><!--<router-link :to="'#'"></router-link>-->
        </ul>
    </div>
    <!--CONTENT-->
    <div class="btn btn-default edit fr margin-bottom-20">
      <span class="padding-right-5" @click="callEdit">{{$t("button.edit")}}</span>
    </div>
    {{ recipeBoxes.length }}
    <div class="wrap_recipes">
      <div class="row0" 
          v-for="(box, $index) in recipeBoxes.slice(0,1)" :key="$index">
        <div class="column1 full fl"
          @click="callRecipeBox(box.id)">
          <div class="wrap_row">
            <div class="circleNum">{{ box.recipe?box.recipe.length:0 }}</div>
            <div class="title">{{ box.name }} {{ box.id }}</div>
            <div class="new" v-if="box.new"></div>
          </div>
          <!-- <div class="photo" v-for="(r, $index) in box.recipe.slice(0,4)" :key="$index"> -->
          <div class="photo" v-for="(r, $index) in box.recipe.slice(0,1)" :key="$index">
            <img v-if="r.mainImgId != null" class="pic" :src="getImgURL(r.mainImgId)" @error="setEmptyImg">
            <img v-else class="pic" src="@/assets/emptyImg.png">
          </div>
        </div>
        <div class="column1 full fl"
          @click="callRecipeBox(recipeBoxes[1].id)">
          <div class="wrap_row">
            <div class="circleNum">{{ recipeBoxes[1].recipe?box.recipe.length:0 }}</div>
            <div class="title">{{ recipeBoxes[1].name }} {{ recipeBoxes[1].id }}</div>
            <div class="new" v-if="recipeBoxes[1].new"></div>
          </div>
          <!-- <div class="photo" v-for="(r, $index) in box.recipe.slice(0,4)" :key="$index"> -->
          <div class="wrap_photo" v-if="recipeBoxes[1].recipe">
            <div class="photo" v-for="(r, $index) in box.recipe.slice(0,1)" :key="$index">
              <img v-if="r.mainImgId != null" class="pic" :src="getImgURL(r.mainImgId)" @error="setEmptyImg">
              <img v-else class="pic" src="@/assets/emptyImg.png">
            </div>
          </div>
          <div class="wrap_photo" v-else>
            <div class="photo">
              <img class="pic" src="@/assets/emptyImg.png">
            </div>
          </div>
        </div>
      </div>
      <!-- <div class="row0">
        <div class="column1 full fl"
          @click="callRecipeBox(recipeBoxes[2].id)">
          <div class="wrap_row">
            <div class="circleNum">{{ recipeBoxes[2].recipe?box.recipe.length:0 }}</div>
            <div class="title">{{ recipeBoxes[2].name }} {{ recipeBoxes[2].id }}</div>
            <div class="new" v-if="recipeBoxes[2].new"></div>
          </div>
          <div class="photo" v-for="(r, $index) in recipeBoxes[2].recipe.slice(0,1)" :key="$index">
            <img v-if="r.mainImgId != null" class="pic" :src="getImgURL(r.mainImgId)" @error="setEmptyImg">
            <img v-else class="pic" src="@/assets/emptyImg.png">
          </div>
        </div>
        <div class="column1 full fl"
          @click="callRecipeBox(recipeBoxes[3].id)">
          <div class="wrap_row">
            <div class="circleNum">{{ recipeBoxes[3].recipe?box.recipe.length:0 }}</div>
            <div class="title">{{ recipeBoxes[3].name }} {{ recipeBoxes[3].id }}</div>
            <div class="new" v-if="recipeBoxes[3].new"></div>
          </div>
          <div class="wrap_photo" v-if="recipeBoxes[3].recipe">
            <div class="photo" v-for="(r, $index) in box.recipe.slice(0,1)" :key="$index">
              <img v-if="r.mainImgId != null" class="pic" :src="getImgURL(r.mainImgId)" @error="setEmptyImg">
              <img v-else class="pic" src="@/assets/emptyImg.png">
            </div>
          </div>
          <div class="wrap_photo" v-else>
            <div class="photo">
              <img class="pic" src="@/assets/emptyImg.png">
            </div>
          </div>
        </div>
      </div> -->
    </div><!-- FIXME: slice(0,1) 만 나오고 recipeBoxes 했을 때는 왜 안나오지? -->
    <!--<div class="wrap_recipes">
      <div class="row0">
        <div class="column1 full fl" 
          v-for="(box, i1) in recipeBoxes.slice(0,1)" :key="i1"
          @click="callRecipeBox(box.id)"
        >
          <div class="wrap_row">
            <div class="circleNum">{{ box.recipe?box.recipe.length:0 }}</div>
            <div class="title">{{ box.name }}</div>
            <div class="new" v-if="box.new"></div>
          </div>
          <div class="photo" v-for="(r, i) in box.recipe.slice(0,4)" :key="i">
            <img v-if="r.mainImgId != null" class="pic" :src="getImgURL(r.mainImgId)" @error="setEmptyImg">
            <img v-else class="pic" src="@/assets/emptyImg.png">
          </div>
        </div>
        <div class="column1 on fl" v-for="(box, i2) in recipeBoxes.slice(1,2)" :key="i2">
          <div class="wrap_row">
            <div class="circleNum">{{ box.recipe?box.recipe.length:0 }}</div>
            <div class="title">{{ box.name }}</div>
            <div class="new" v-if="box.new"></div>
          </div>
          <div class="photo" v-for="(r, i) in box.recipe.slice(0,1)" :key="i">
            <img v-if="r.mainImgId != null" class="pic" :src="getImgURL(r.mainImgId)" @error="setEmptyImg">
            <img v-else class="pic" src="@/assets/emptyImg.png">
          </div>
        </div>
      </div>
      <div class="row0">
        <div class="column1 off fl" v-for="(box, i3) in recipeBoxes.slice(2,3)" :key="i3">
          <div class="wrap_row">
            <div class="circleNum">{{ box.recipe?box.recipe.length:0 }}</div>
            <div class="title">{{ box.name }}</div>
            <div class="new" v-if="box.new"></div>
          </div>
          <div class="photo" v-for="(r, i) in box.recipe.slice(0,1)" :key="i">
            <img v-if="r.mainImgId != null" class="pic" :src="getImgURL(r.mainImgId)" @error="setEmptyImg">
            <img v-else class="pic" src="@/assets/emptyImg.png">
          </div>
        </div>
        <div class="column1 off fl" v-for="(box, i4) in recipeBoxes.slice(2,3)" :key="i4">
          <div class="wrap_row">
            <div class="circleNum">{{ box.recipe?box.recipe.length:0 }}</div>
            <div class="title">{{ box.name }}</div>
            <div class="new" v-if="box.new"></div>
          </div>
          <div class="photo" v-for="(r, i) in box.recipe.slice(0,1)" :key="i">
            <img v-if="r.mainImgId != null" class="pic" :src="getImgURL(r.mainImgId)" @error="setEmptyImg">
            <img v-else class="pic" src="@/assets/emptyImg.png">
          </div>
        </div>
      </div>
      <div class="row0">
        <div class="column1 full fl"
          @click="callRecipeBox(60)"
        >
          <div class="wrap_row">
            <div class="circleNum">7</div>
            <div class="title">{{ boxName }}</div>
            <div class="new" v-if="isActive"></div>
          </div>
          <div class="photo">
            <img src="../assets/images/recipephoto-2.png" alt=""/>
          </div>
        </div>
        <div class="column1 on fl"
          v-for="(box, i5) in recipeBoxes" :key="i5"
          @click="callRecipeBox(box.id)">
          <div class="wrap_row">
            <div class="circleNum">3</div>
            <div class="title">박스이름</div>
            <div class="new" v-if="false"></div>
          </div>
          <div class="photo">
            <img src="../assets/images/recipephoto-3.png" alt=""/>
          </div>
        </div>
      </div>
      <div class="row0">
        <div class="column1 new fl" v-on:click.capture="addNewBox(boxName)">
          <div class="wrap_row">
            <div class="title">{{ $t('button.addNewBox') }}</div>
          </div>
          <div class="photo">
            <img src="@/assets/images/icon_plus.png" alt="$t('button.addNewBox')"/>
          </div>
        </div>
      </div>
    </div>--><!--wrap_recipes-->
    </main>
    <!--FOOTER-->
  </div>
</template>

<script>
import emptyImg from '@/assets/emptyImg.png'
import ConfirmInput from 'vue-confirm-input'

export default {
  name : "RecipeBoxListView",
  data: ()=>({
    recipeBoxes : [],
    tempBoxes : [],
    mainPicture : '',
    boxName: '기본박스',
    recipeId: 0,
    isActive: false,
  }),

  computed: {
    reversedMesage: {
      get() {
        return this.boxName.split('').reverse().join('')
      },
      set(value) {
        console.log(value)
        this.boxName = value
      }
    }
  },

  watch: {
    boxName(newValue) {
      console.log('msg:', newValue)
    },
    reversedMesage(newValue) {
      console.log('reversedMessage:', newValue)
    },
    recipeBoxes(newValue) {
      console.log('recipeBoxes:', newValue)
    }
  }, 

  created() {
    this.initialize();
  },
  
  mounted() {
    this.$checkToken('recipeboxlist');
  },

  components: {
      ConfirmInput
  },

  methods: {
    initialize() {
      this.getRecipeBoxAll();
    },

    async getRecipeBoxAll() {      
      const response = await this.$api(
        `${this.$API_SERVER}/api/recipebox/mine`,
        "get"
      );
      if (response.status === this.HTTP_OK) {
        this.recipeBoxes = response.data;
        console.log(this.recipeBoxes);
      }
    },

    callEdit() {
      console.log("Edit");
      // TODO : 폴더 편집 - 폴더명 변경, 삭제, 폴더 새로 만들기 기능
    },

    async addNewBox(name) {
        name = prompt('새 박스 이름를 입력하고 확인버튼을 눌러주세요.', '새 박스 이름');
        if(name === null){
          alert("취소되었습니다.")
          return
        }
        if(name.length < 2 || name.length > 20 || name === ' '){ /* TODO: 특수문자 처리 */
          alert("박스 이름은 공백을 제외한 글자수 2 이상에서 20 이하로 작성해 주세요.")
          return
        }
        console.log("addNewBox : "+name);
        this.boxName = name;
        const response = await this.$api(
          `${this.$API_SERVER}/api/recipebox/${name}`,
          "post"
        );
        if (response.status === this.HTTP_OK) {
            this.selectedRecipeBox = response.data;
        }
        this.initialize();
    },

    setEmptyImg(e) {
      e.target.src=emptyImg;
    },
    
    async getFileId(id){
        const response = await this.$api(
          `${this.$API_SERVER}/file?contentsId=${id}`,
          "get"
        );
        if (response.status === this.HTTP_OK) {
            console.log(response.data);
        }
    },

    getImgURL(id) {
      const url = `${this.$API_SERVER}/file/download/thumbnail?fileId=` + id;
      console.log(url);
      return url
    },

    callRecipeBox(id) {
        location.href=`/recipebox/${id}`;
    },

    activate() {
      this.isActive = !this.isActive
    }
  },
}
</script>

<style scoped>
/* button
{
    background: inherit ; 
    border:none; 
    box-shadow:none; 
    border-radius:0; 
    padding:0; 
    overflow:visible; 
    cursor:pointer
}
ul {
    list-style: none;
}
li {
    float: left;
} */
.active {
  color: orange;
}
/* 
main.recipebox .wrap_recipes .column1.on {width: 50%; }
main.recipebox .wrap_recipes .column1.on .wrap_row {height:24px;}
main.recipebox .wrap_recipes .column1.on .wrap_row .circleNum {position: absolute; margin-top:-7px; z-index: 99999; border-radius: 10px;width:20px;background-color:#FF9519; color: #fff;text-align: center; font-weight: 600;}
main.recipebox .wrap_recipes .column1.on .wrap_row .title     {float: left; z-index: -1;border: 3px #FF9519 solid; border-bottom: none; border-radius: 10px 10px 0 0; padding:0 5px 0 6px; margin-left:13px;background-color:#fff; color: #FF9519;text-align: center;}
main.recipebox .wrap_recipes .column1.on .wrap_row .new       {float: left;margin-top:5px; background: url(../images/bul-new.png)50% 50% no-repeat;width:35px;height: 16px;display:block;}
main.recipebox .wrap_recipes .column1.on .photo     {padding: 0;margin: 0 10px 0 0; display:block; border: 3px #FF9519 solid; border-radius: 5px; box-shadow: 2px 2px 2px 0 #ccc;}
main.recipebox .wrap_recipes .column1.on .photo img {width: 100%; height: 100%;}
*/
</style>
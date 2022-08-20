<template>
  <div>
    <main class="recipebox">
      <!--HEADER-->    
      <BoxListMenu :key="recipeId"></BoxListMenu>
      <!--CONTENT-->
      <div class="btn btn-default edit fr margin-bottom-20">
        <span class="padding-right-5" @click="callEdit">{{$t("button.edit")}}</span>
      </div>
      <!-- <BoxListView :key="listView"></BoxListView> -->
      <div class="wrap_recipes">
          <div class="row0">
              <div class="column1 full fl" 
              v-for="(box, $index) in allBoxInfo" :key="$index+'a'"
              @click="callRecipeBox('all')"
              >
                  <div>
                      <div class="wrap_row">
                          <div class="circleNum">{{ box.recipeCnt }}</div>
                          <div class="title">{{ box.name }}</div>
                          <div class="new" v-if="box.new"></div>
                      </div>
                      <div v-if="box.thumbnails">
                          <div class="photo" style="display:inline-block;">
                            <div>
                              <div class="fl" style="width:50%; height:50%;" v-for="(img, $i) in box.thumbnails.slice(0,1)" :key="$i+'1'">
                                <img v-if="img.mainImgId != null" class="pic" :src="getImgURL(img.mainImgId)" @error="setEmptyImg">
                                <img v-else class="pic" src="@/assets/emptyImg.png">
                              </div>
                              <div class="fl" style="width:50%; height:50%;" v-for="(img, $i) in box.thumbnails.slice(1,2)" :key="$i+'2'">
                                <img v-if="img.mainImgId != null" class="pic" :src="getImgURL(img.mainImgId)" @error="setEmptyImg">
                                <img v-else class="pic" src="@/assets/emptyImg.png">
                              </div>
                            </div>
                            <div>
                              <div class="clear-both fl" style="width:50%; height:50%;" v-for="(img, $i) in box.thumbnails.slice(2,3)" :key="$i+'3'">
                                <img v-if="img.mainImgId != null" class="pic" :src="getImgURL(img.mainImgId)" @error="setEmptyImg">
                                <img v-else class="pic" src="@/assets/emptyImg.png">
                              </div>
                              <div class="fl" style="width:50%; height:50%;" v-for="(img, $i) in box.thumbnails.slice(3,4)" :key="$i+'4'">
                                <img v-if="img.mainImgId != null" class="pic" :src="getImgURL(img.mainImgId)" @error="setEmptyImg">
                                <img v-else class="pic" src="@/assets/emptyImg.png">                                
                              </div>
                            </div>
                          </div>
                      </div>
                      <div v-else>
                          <div class="photo">
                              <img class="pic" src="@/assets/emptyImg.png">
                              <img class="pic" src="@/assets/emptyImg.png">
                              <img class="pic" src="@/assets/emptyImg.png">
                              <img class="pic" src="@/assets/emptyImg.png">
                          </div>
                      </div>
                  </div>
              </div>
              <div class="column1 fl" 
              v-for="(box, $index) in boxList" :key="$index+'b'"
              @click="callRecipeBox(box.id)"
              >
                  <div v-if="!box.isDefault && box.recipe"><!-- 빈 박스(폴더)는 비활성화 -->
                  <div v-if="box.recipe.length > 0">
                      <div class="wrap_row">
                          <div class="circleNum">{{ box.recipe?box.recipe.length:0 }}</div>
                          <div class="title">{{ box.name }}</div>
                          <div class="new" v-if="box.new"></div>
                          <button hidden v-on:click="callDeleteBox(box.id)">X</button>
                      </div>
                      <div v-if="box.recipe">
                          <div class="photo" v-for="(r, $i) in box.recipe.slice(0,1)" :key="$i">
                              <img v-if="r.mainImgId != null" class="pic" :src="getImgURL(r.mainImgId)" @error="setEmptyImg">
                              <img v-else class="pic" src="@/assets/emptyImg.png">
                          </div>
                      </div>
                  </div>
                  </div>
              </div>
          </div>
      </div>
      <div class="wrap_recipes">
          <div class="row0">
              <div class="column1 new fl" v-on:click.capture="addNewBox(boxName)">
                  <div class="wrap_row">
                      <div class="title">{{ $t('content.newBox') }}</div>
                  </div>
                  <div class="photo">
                      <img src="@/assets/images/icon_plus.png" alt="새로운 박스"/>
                  </div>
              </div>
          </div>
      </div>
      <!-- <div>
        <infinite-loading @infinite="infiniteHandler">
            <div slot="no-more"><br/></div>
        </infinite-loading>
      </div> -->
    </main>
    <!--FOOTER-->
  </div>
</template>

<script>
import emptyImg from '@/assets/emptyImg.png'
import ConfirmInput from 'vue-confirm-input'
import InfiniteLoading from 'vue-infinite-loading';
import { mapActions } from "vuex";
import BoxListMenu from "../components/BoxListMenu.vue";

export default {
  name : "RecipeBoxListView",
  data: ()=>({
    page : 0,
    boxList : [],       // 화면에 보여줄 담은 레시피 목록
    recipeBoxes : [],   // 서버로 부터 얻어온 담은 레시피 목록
    allBoxInfo : [],    // 모아 보기
    boxName: '기본박스',
    recipeId: 0,
  }),

  components: {
    ConfirmInput,
    InfiniteLoading,
    BoxListMenu
  },

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

  created() {
    this.recipeId = this.$route.params.recipeId
    this.initialize();
  },
  
  mounted() {
    this.$checkToken('recipeboxlist');
  },

  watch: {
  }, 

  methods: {
    ...mapActions('box', ['setStep','setBoxId', 'setAllBox', 'setRecipeId']),

    initialize() {
      this.setRecipeId(this.recipeId)
      this.getRecipeBoxAll();
    },
    
    async getRecipeBoxAll() {
      let params = '';
        // params += `?page=${this.page}`;
        // params += '&sort=createdAt,DESC';
      const response = await this.$api(`${this.$API_SERVER}/api/reciperecipebox/recipe/mine`+params, "get");
      if (response.status === this.HTTP_OK) {
        this.allBoxInfo = []
        // this.setAllBoxInfo(allBoxInfo)
        this.recipeBoxes = response.data;

        let recipeCnt = 0
        let thumbnails = []
        let newFlag = false
        this.recipeBoxes.forEach(box => {
          if(box.recipe){
              box.recipe.forEach(recipe => {
              recipeCnt = recipeCnt + 1
              if(recipe.mainImgId) thumbnails.push({"mainImgId": recipe.mainImgId})
              if(recipe.new) newFlag = true
            });
          }
        });
        this.allBoxInfo.push({
          'name': '모든 레시피',
          'recipeCnt': recipeCnt,
          'thumbnails': thumbnails,
          'new': newFlag,
          'recipeBoxes': this.recipeBoxes
          });
        this.boxList = this.recipeBoxes
        // console.log(this.allBoxInfo)
        this.setAllBox(this.allBoxInfo)
      }
    },

    async infiniteHandler($state){
      let params = `page=${this.page}`;
        // params += '&sort=createdAt,DESC';
      console.log(params);
      const response = await this.$api(`${this.$API_SERVER}/api/recipebox/mine?`+params, `get`);
      if (response.status === this.HTTP_OK) {
        if(response.data.length){
          console.log(response.data);
          for(const data of response.data){
            this.boxList.push(data);
            // this.setAllBoxInfo(data);
          }          
          this.page++;
          $state.loaded();
          if(response.data.length / 2 < 1){
            $state.complete();
          }
        }else{
          $state.complete();
        }
      }else{
        console.log(response.status);
        $state.complete();
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

    getImgURL(id) {
      const url = `${this.$API_SERVER}/file/download/thumbnail?fileId=` + id;
      console.log(url);
      return url
    },

    callRecipeBox(id) {
      this.setBoxId(id)
      this.setStep(4)
      this.$router.push(`/recipebox/${id}`)

    },
  },
}
</script>

<style scoped>
button
{
    background: inherit ; 
    border:none; 
    box-shadow:none; 
    border-radius:0; 
    padding:0; 
    overflow:visible; 
    cursor:pointer
}
</style>
<template>
    <div>
        <div>
        <!--HEADER-->
            <h4>{{ $t("title.recipeBox") }}</h4>
            <div class="navbar">
                <ul class="subnav">                    
                    <li><router-link :to="'/myrecipe'">{{ $t("menu.myRecipe") }}</router-link></li>
                    <li><router-link :to="'/recipeboxlist'">{{ $t("menu.savedRecipe") }}</router-link></li>
                    <li><router-link :to="'#'">{{ $t("menu.historyRecipe") }}</router-link></li>
                </ul>
            </div>
            
            <div class="boxes-wrap">
                <div class="boxes swiper-container swiper-navigation">
                    <swiper class="swiper" ref="filterSwiper" :options="swiperOption" role="tablist">
                        <swiper-slide class="swiper-slide" role="tab" 
                            v-for="(item, index) in recipeBoxes"
                            :key="index">
                            <div>
                                <button @click="selectRecipeBox(item.id)">{{item.name}}<span hidden>({{item.id}})</span></button>
                            </div>
                        </swiper-slide>
                        <swiper-slide role="tab">
                            <div>
                                <button @click="addNewBoxPage()">+{{ $t('button.addNewBox') }}</button>
                            </div>
                        </swiper-slide>
                    </swiper>
                    <!-- <div class="wrapper">
                    <div class="swiper-pagination" slot="pagination"></div>
                    <div class="swiper-button-prev" slot="button-prev"></div>
                    <div class="swiper-button-next" slot="button-next"></div>
                    </div> -->
                </div>
                <div>
                    <span hidden>선택된 레시피박스: {{ selectedRecipeBox.name }}[{{ selectedRecipeBox.id }}]</span>
                </div>
            </div>
            
        <!--CONTENTS-->
            <div class="contents" v-if="step===1">
                {{$t("content.all")}} {{ recipeList.length }}
                <button @click="callEdit">{{$t("button.edit")}}</button>
                <table>
                    <thead>
                        <td></td>
                        <td></td>
                    </thead>
                    <tr v-for="(recipe, index) in recipeList.slice(0,5)" :key="index"><!-- 최대 5개 -->
                        <td>
                            <p>
                                <img :src="recipe.file" width="200px" height="150px" @error="setEmptyImg">
                            </p>
                        </td>
                        <td>
                            <router-link :to="'/recipedetail/'+recipe.recipeId">
                            {{recipe.title}} <br/>
                            {{recipe.subTitle}} <br/>
                            <ul v-for="(period, idx) in $t('option.period')" :key="idx">
                                <li v-if="recipe.period == idx">{{period}} {{recipe.boxName}}|{{recipe.recipeId}}|{{recipe.boxId}}</li>
                            </ul>
                            </router-link>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="contents" v-if="step===2">
                {{$t("content.all")}} {{ recipeList.length }}
                <button @click="cancel">{{$t("button.cancel")}}</button>&nbsp;
                <button @click="done">{{$t("button.done")}}</button>
                <table>
                    <thead>
                        <td></td>
                        <td></td>
                        <td></td>
                    </thead>
                    <tr v-for="(recipe, index) in recipeList.slice(0,5)" :key="index"><!-- 최대 5개 -->
                        <td>
                            <p>
                                <img :src="recipe.file" width="200px" height="150px" @error="setEmptyImg">
                            </p>
                        </td>
                        <td>
                            <router-link :to="'/recipedetail/'+recipe.recipeId">
                            {{recipe.title}} <br/>
                            {{recipe.subTitle}} <br/>
                            <ul v-for="(period, idx) in $t('option.period')" :key="idx">
                                <li v-if="recipe.period == idx">{{period}} {{recipe.boxName}} {{recipe.recipeId}} {{recipe.boxId}}</li>
                            </ul>
                            </router-link>
                        </td>
                        <td>
                            <input
                                type="checkbox"
                                id=index
                                :value=recipe.recipeId 
                                v-model="checkedRecipeIds"
                            >
                            <!-- <label for=index>{{recipe.title}}</label> -->
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <span>체크한 이름: {{ checkedRecipeIds }}</span>
                        </td>
                    </tr>
                </table>

                <div class="moveBox" v-if="moveStep===0">
                    <button @click="callMoveRecipe">{{$t("button.move")}}</button>&nbsp;
                    <button @click="callDeleteRecipe">{{$t("button.delete")}}</button>
                    <!-- <button @click="deleteRecipe">{{$t("button.delete")}}</button> -->
                </div>
                <div class="moveBox" v-if="moveStep===1">
                    <div class="moveBoxHeader">
                        <ul>
                            <li>{{$t("content.moveBox")}}</li>
                            <li>
                                <button @click="addNewBoxPage()">+{{ $t('button.addNewBox') }}</button>
                            </li>
                        </ul>
                    </div>
                    <div class="moveBoxBody">
                        <ul v-for="(box, index) in recipeBoxes" :key="index">
                            <li @click="callMoveRecipeBox(box.id)">
                                <button v-on:click="callDeleteBox(box.id)">X</button>
                                <p>
                                    <img :src="box.recipe?box.recipe[0].mainImgId:''" width="200px" height="150px" @error="setEmptyImg">
                                </p>
                                {{box.name}}
                            </li>
                        </ul>
                    </div>
                    <div class="moveBoxFooter">
                        <button @click="cancel">{{$t("button.cancel")}}</button>
                    </div>
                </div>
            </div>
            <br/>
            <div class="contents" id="new-box" v-if="step===3">
                <fieldset>
                    <legend>새 박스 추가</legend>
                    <form v-on:submit="onSubmitForm">
                        <label for="newBox">새로운 박스의 이름을 입력해 주세요.</label><br/>
                        <input type="text" v-model="newBox" maxlength="10"> <button>추가</button>
                    </form>
                </fieldset>
                <label id="result-label" hidden for="result"></label><br/>
                <button @click="cancel">{{$t("button.cancel")}}</button>
                <!-- <button @click="done">{{$t("button.done")}}</button> -->
            </div>
        <!--FOOTER-->
        </div>
    </div>
</template>
<script>
import emptyImg from '@/assets/emptyImg.png'
import { swiper, swiperSlide } from "vue-awesome-swiper";
import "swiper/dist/css/swiper.min.css";

export default {
    name : "RecipeBoxView",
    data: ()=>({
        swiperOption: {
            slidesPerView: 4,   // 'auto'
            spaceBetween: 10,   // swiper-slide 사이의 간격 지정
            slidesOffsetBefore: 0, // slidesOffsetBefore는 첫번째 슬라이드의 시작점에 대한 변경할 때 사용
            slidesOffsetAfter: 0, // slidesOffsetAfter는 마지막 슬라이드 시작점 + 마지막 슬라이드 너비에 해당하는 위치의 변경이 필요할 때 사용
            freeMode: true, // freeMode를 사용시 스크롤하는 느낌으로 구현 가능
            centerInsufficientSlides: true, // 컨텐츠의 수량에 따라 중앙정렬 여부를 결정함
            slideToClickedSlide: true,
            pagination: {
                el: ".swiper-pagination",
                clickable: true,
            },
                navigation: {
                nextEl: ".swiper-button-next",
                prevEl: ".swiper-button-prev",
            },
        },
        recipeBoxes : [],
        selectedRecipeBox : [],
        /* 
            step=0 레시피 박스 화면 RecipeBoxListView.vue 로 분리
            step=1 레시시 박스 상세 화면
            step=2 레시피 박스 편집 화면
            step=3 새 레시피 박스 입력 화면
        */
        step : 1,
        /* 
            moveStep=0 레시피 박스 편집 화면
            moveStep=1 레시시 박스 편집 > 이동 화면
        */
        moveStep : 0,
        recipeList : [],
        mainPicture : '',
        checkedRecipeIds : [],
        tempMap : {},
        boxName : '기본박스',
        boxId : 0,
        newBox : "",
        edit : '',
        tempBoxId : [],
    }),
    created() {
        this.boxId = this.$route.params.boxId;
        this.initialize();
    },
    methods : {
        initialize() {
            this.getRecipeBoxById(this.boxId);
            this.getRecipeBoxList();
            this.getRecipeRecipeBoxList(this.boxId);
        },
        async getRecipeBoxList() {            
            const response = await this.$api(
            `http://localhost:8090/api/recipebox/mine`,
            "get"
            );
            if (response.status === this.HTTP_OK) {
                this.recipeBoxes = response.data;
            }
        },
        async getRecipeBoxById(id) {
            const response = await this.$api(
            `http://localhost:8090/api/recipebox/${id}`,
            "get"
            );
            if (response.status === this.HTTP_OK) {
                this.selectedRecipeBox = response.data;
            }
        },
        async getRecipeRecipeBoxList(id) {
            this.recipeList = [];
            const response = await this.$api(
            `http://localhost:8090/api/reciperecipebox/recipe`,
            "get",
            {box: id}
            );            
            if (response.status === this.HTTP_OK) {
                console.log(response.data);
                response.data.forEach( obj => {
                    this.recipeList.push({
                        title: obj.title,
                        subTitle: obj.subTitle,
                        score: obj.score,
                        timeTaken: obj.timeTaken,
                        period: obj.period,
                        recipeId: obj.recipeId,
                        contentsId: obj.contentsId,
                        fileId: obj.fileId,
                        file: obj.fileId?`http://localhost:8090/file/download/thumbnail?fileId=${obj.fileId}`:this.mainPicture,
                        boxName: this.selectedRecipeBox.name?this.selectedRecipeBox.name:"기본박스",
                        boxId: this.selectedRecipeBox.id,
                        commentsNumber : 66   // TODO: comments
                    })
                });                 
            }
        },
        setEmptyImg(e) {
            e.target.src=emptyImg;
        },
        selectRecipeBox(id) {
            console.log(`selectRecipeBox: ${id} boxId: ${this.boxId}`);
            location.href=`/recipebox/${id}`;
        },
        moveRecipeBox() {
            this.checkedRecipeIds.forEach(async (item, index, arr) => {
                this.tempBoxId.forEach(async (id, index, arr) => {     
                // console.log(`${this.selectedRecipeBox.id}?recipe=${item}&to=${id}`);           
                    const response = await this.$api(
                    `http://localhost:8090/api/reciperecipebox/${this.selectedRecipeBox.id}`,
                    "post",
                    {
                        recipe: item,
                        to: id
                    }
                    );
                    if (response.status === this.HTTP_OK) {
                        console.log("moveRecipeBox:", response.data);
                    }
                });
            });
            //this.getRecipeBoxById(this.boxId)
            //this.getRecipeRecipeBoxList(this.boxId);
        },
        addNewBoxPage() {
            console.log("addNewBoxPage step=2")
            this.step = 3
        },
        onSubmitForm(e) {
            console.log("onSubmitForm : "+e);
            let text = `"${this.newBox}"로 새 레시피 박스를 추가하시겠습니까?`;
            if (confirm(text) == true) {
                this.addNewBox(this.newBox);
                text = "추가 되었습니다.";
            } else {
                text = "취소 되었습니다.";
            }
            let element = document.getElementById("result-label");
            let hidden = element.getAttribute("hidden");
            if (hidden) {
                element.removeAttribute("hidden");
                element.innerText = text;
            } else {
                element.setAttribute("hidden", "hidden");
                element.innerText = text;
            }
            e.preventDefault();
        },
        async addNewBox(name) {
            console.log("addNewBox : "+name);
            this.boxName = name;
            const response = await this.$api(
            `http://localhost:8090/api/recipebox/${name}`,
            "post"
            );
            if (response.status === this.HTTP_OK) {
                this.selectedRecipeBox = response.data;
            }
            this.initialize();
        },
        async deleteBoxId() {
            this.tempBoxId.forEach( async(id, index, arr) => {
                console.log("deleteBoxId: "+id);
                const response = await this.$api(
                `http://localhost:8090/api/recipebox/${id}`,
                "delete"
                );
                if (response.status === this.HTTP_OK) {
                    console.log(response.data);
                }
            })
            this.initialize();
        },
        deleteRecipe() {
            console.log("Delete")            
            // 선택된 레시피박스의 checked 된 recipeId 를 삭제한다.
            this.checkedRecipeIds.forEach(async (item, index, arr) => {
                console.log(`${this.selectedRecipeBox.id}?recipe=${item}`);
                const response = await this.$api(
                `http://localhost:8090/api/reciperecipebox/${this.selectedRecipeBox.id}`,
                "delete",
                { recipe: item }
                );
                if (response.status === this.HTTP_OK) {
                    console.log(response.data);
                }
            });
            this.getRecipeRecipeBoxList(this.boxId);
        },
        callEdit() {
            console.log("Edit");
            this.step = 2
        },
        callMoveRecipe() {
            console.log("Move")
            this.moveStep = 1
        },
        callMoveRecipeBox(id){
            if(this.checkedRecipeIds.length == 0) return
            this.tempBoxId.push(id);
            this.tempMap = new Map();
            this.recipeList.forEach((recipe, index, arr) => {
                this.tempMap.set(index, recipe);
            });
            this.checkedRecipeIds.forEach((recipeId, index, arr) => {
                this.recipeList.forEach((recipe, index, arr) => {
                    if(recipeId == recipe.recipeId){
                        this.$delete(arr, index)
                    }
                });
            });
            this.edit = 'move';
        },
        callDeleteBox(id){
            this.tempBoxId.push(id);
            this.tempMap = new Map();
            this.recipeBoxes.forEach((box, index, arr) => {
                this.tempMap.set(index, box);
            });
            this.recipeBoxes.forEach((box, index, arr) => {
                if(id == box.id){
                    this.$delete(arr, index)
                }
            });
            this.edit = 'deleteBox';
        },
        callDeleteRecipe(){
            this.tempMap = new Map();
            this.recipeList.forEach((recipe, index, arr) => {
                this.tempMap.set(index, recipe);
            });
            // view 화면에서 숨기기
            this.checkedRecipeIds.forEach((recipeId, index, arr) => {
                this.recipeList.forEach((recipe, index, arr) => {
                    if(recipeId == recipe.recipeId){
                        this.$delete(arr, index)
                    }
                });
            });
            this.edit = 'deleteRecipe';

        },
        cancel() {
            console.log("cancel")
            if(this.edit == "move"){
                this.checkedRecipeIds = [];
                this.recipeList = [];
                for(let i=0;i<this.tempMap.size;i++){
                    this.recipeList.push(this.tempMap.get(i))
                };
            }
            if(this.edit == "deleteBox"){
                this.recipeBoxes = [];
                for(let i=0;i<this.tempMap.size;i++){
                    this.recipeBoxes.push(this.tempMap.get(i))
                };
            }
            if(this.edit == "deleteRecipe"){
                this.checkedRecipeIds = [];
                this.recipeList = [];
                for(let i=0;i<this.tempMap.size;i++){
                    this.recipeList.push(this.tempMap.get(i))
                };
            }
            this.step = 1
            this.moveStep = 0
            this.initialize();
        },
        done() {
            console.log("done")
            if(this.edit == "move"){
                this.moveRecipeBox()
            }
            if(this.edit == "deleteBox"){
                this.deleteBoxId()
            }
            if(this.edit == "deleteRecipe"){
                this.deleteRecipe()
            }
            this.step = 1
            this.moveStep = 0
            //this.initialize();
        },
    },
    components: {
        swiper,
        swiperSlide,
    },    
    computed: {
        swiper() {
            console.log("computed:swiper");
            return this.$refs.filterSwiper.swiper;
        },
        // isOverview() {
        //     console.log("computed:isOverview");
        //     return window.innerWidth >= this.swiper.virtualSize
        // }
    },
    mounted () {
        console.log("mounted");
        // this.swiperInit();
        // this.$nextTick(() => {
        //     const swiperOption = this.$refs.filterSwiper.swiper;
        //     swiperOption.controller.control = filterSwiper;
        // });
        // console.log(swiper)
    },
}
</script>
<style lang="scss" scoped>
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
.swiper-container {
  padding: 0 20px;
  &:before,
  &:after {
    display: block;
    position: absolute;
    top: 0;
    width: 20px;
    height: 100%;
    z-index: 10;
    content: "";
  }
  &:before {
    left: 0;
    background: linear-gradient(90deg, #fff -20.19%, rgba(255, 255, 255, 0.8) 18.31%, rgba(255, 255, 255, 0) 75%);
  }
  &:after {
    right: 0;
    background: linear-gradient(270deg, #fff -20.19%, rgba(255, 255, 255, 0.8) 18.31%, rgba(255, 255, 255, 0) 75%);
  }
  .swiper-wrapper {
    .swiper-slide {
      width: auto;
      min-width: 56px;
      padding: 0px 14px;
      font-size: 14px;
      line-height: 36px;
      text-align: center;
      color: #84868c;
      border: 0;
      border-radius: 18px;
      background: #f3f4f7;
      appearance: none;
      cursor: pointer;
      &[aria-selected="true"] {
        color: #fff;
        background: #000;
      }
    }
  }
}
.navbar ul {
    list-style: none;
}
.contents ul {
    list-style: none;
}
.contents li {
    float: left;
    margin: 1px 6px;
}
</style>

<template>
  <div>
    <main class="recipebox">
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
        <div class="boxes-wrap">
            <div class="wrap_keywords boxes swiper-container swiper-navigation">
                <swiper class="swiper" ref="filterSwiper" :options="swiperOption" role="tablist">
                    <swiper-slide class="keywords" role="tab" 
                        v-for="(item, index) in recipeBoxes"
                        :key="index"><!-- class="swiper-slide" -->
                        <div>
                            <button @click="selectRecipeBox(item.id)">{{item.name}} <span hidden>({{item.id}})</span></button>
                        </div>
                    </swiper-slide>
                    <swiper-slide role="tab">
                        <div>
                            <button @click="addNewBoxPage()">{{ $t('button.addNewBox') }} +</button>
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
        <div v-if="step===1">
            <div class="wrap_allnum fl">
                <span class="dp-inline-block fl">{{$t("content.all")}} </span>
                <span class="num">{{ recipeList.length }}</span>
            </div>
            <div class="btn btn-default edit fr">
                <span class="padding-right-5" @click="callEdit">{{$t("button.edit")}}</span>
            </div>
            <div class="wrap_recipes">
                <div class="alltitle">{{ selectedRecipeBox.name }}</div>
                <div class="wrap_in" v-for="(recipe, index) in recipeList.slice(0,5)" :key="index">
                <router-link :to="'/recipedetail/'+recipe.recipeId">
                    <div class="photo fl"><img :src="recipe.file"/></div>
                    <div class="wrap_text fl">
                        <div class="wrap_bullet">
                            <span v-for="(period, idx) in $t('option.period')" :key="idx">
                            <div class="squre4 fl" v-if="recipe.period == idx">{{ period[0] }}</div>
                            </span>
                            <div class="new2 fl" v-if="recipe.new"></div>
                        </div>
                        <div class="fr"></div>
                        <div class="title">
                            <div class="fl padding-right-10">{{ recipe.title }}</div>
                            <span class="icon_reply fl"></span>
                        </div>
                        <div class="text">{{ recipe.subTitle }}</div>
                    </div>
                </router-link>
                </div>
            </div>
        </div>
        <div v-if="step===2">
            <div class="wrap_allnum fl contents">
                <div class="fl" v-on:click="on = !on">
                    <span class="select dp-inline-block fl margin-right-5" 
                        :class="{on : on}" 
                        @click="callAllSelect"
                    />
                    <span class="dp-inline-block">{{$t("content.selectAll")}}</span>
                </div>
            </div>
            <div class="btn btn-default fr margin-right-5" @click="done">
                <span>{{$t("button.done")}}</span>
            </div>
            <div class="btn btn-default fr margin-right-5" @click="cancel">
                <span>{{$t("button.cancel")}}</span>
            </div>
            <div class="wrap_recipes">
                <div class="alltitle hidden">{{ selectedRecipeBox.name }}</div>
                <div class="wrap_in" v-for="(recipe, index) in recipeList.slice(0,5)" :key="index">
                    <div class="photo fl">
                        <img :src="recipe.file"/>
                    </div>
                    <div class="wrap_text fl">
                        <div class="wrap_bullet">
                            <span v-for="(period, idx) in $t('option.period')" :key="idx">
                            <div class="squre4 fl" v-if="recipe.period == idx">{{ period[0] }}</div>
                            </span>
                            <div class="new2 fl" v-if="recipe.new"></div>
                        </div>
                        <div class="fr">
                            <span class="select dp-inline-block fl margin-right-5"
                                :class="{on : selectedRecipeIds.includes(recipe.recipeId)}" 
                                @click="setSelectedRecipe(recipe.recipeId)"
                            />
                        </div>
                        <div class="title">
                            <div class="fl padding-right-10">{{ recipe.title }}</div>
                            <span class="icon_reply fl"></span>
                        </div>
                        <div class="text">{{ recipe.subTitle }}</div>
                    </div>
                </div>
            </div>
            <div>
                <span hidden>체크한 이름: {{ selectedRecipeIds }}</span>
            </div>
            
            <div class="select_done" v-if="moveStep===0">
                <div>
                    <span class="select on fl margin-right-5"></span>
                    <span class="dp-inline-block fl">{{$t("content.select")}} {{ selectedRecipeIds.length }}/{{ recipeList.length }}</span>
                </div>
                <div class="wrap_select0">
                <ul >
                    <li class="menu1 fl" style=" cursor: pointer;" @click="callMoveRecipe">{{$t("button.move")}}</li>
                    <li class="menu2 fl" @click="callDeleteRecipe">{{$t("button.delete")}}</li>
                </ul>
                </div>
            </div>
            <div class="select_done" v-if="moveStep===1">
                <div>
                    <div class="fl margin-bottom-5">
                    <span class="select on fl margin-right-5"></span>
                    <span class="dp-inline-block fl">{{$t("content.select")}} {{ selectedRecipeIds.length }}/{{ recipeList.length }}</span>
                    </div>
                    <div class="fr b color-grey2 margin-bottom-5" style="margin-top: -28px;" @click="addNewBoxPage()">{{ $t('button.addNewBox') }} +</div>
                </div>
                <div class="wrap_select">
                    <ul v-for="(box, index) in recipeBoxes" :key="index">
                        <li class="menu fl" @click="callMoveRecipeBox(box.id)">
                            <!-- <button class="btn btn-default fr" v-on:click="callDeleteBox(box.id)">X</button> -->
                            {{ box.name }}
                        </li>
                    </ul>
                    <div class="arr_right">더보기</div>
                </div>
                <div class="btn btn-default" style="width:112px;" @click="cancel">{{$t("button.cancel")}}</div>
            </div>
        </div>
        <div class="wrap_newbox" id="new-box" v-if="step===3">
            <!-- <fieldset class="wrap_in"> -->
                <!-- <legend>새 박스 추가</legend> -->
                <form v-on:submit="onSubmitForm">
                    <label class="title" for="newBox">새 박스 추가</label><br/>
                    <input class="width80" type="text" v-model="newBox" maxlength="10" placeholder="새로운 박스의 이름을 입력해 주세요."> <div class="btn btn-default"><button style="none">추가</button></div>
                </form>
            <!-- </fieldset> -->
            <div>
            <label id="result-label" hidden for="result"></label><br/>
            <div class="btn btn-default fl margin-bottom-40" @click="cancel">{{$t("button.cancel")}}</div>
            <div class="btn btn-default fl" @click="done">{{$t("button.done")}}</div>
            </div>
        </div>
    </main>
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
            // on {
            //     click: function() {
            //         this.activeTab()
            //     },
            //     tap: function() {
            //         this.activeTab()
            //     },
            // },
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
        selectedRecipeIds : [],
        recipeIds : [],
        tempMap : {},
        boxName : '기본박스',
        boxId : 0,
        newBox : "",
        edit : '',
        tempBoxId : [],
        recipeId: 0, // TODO: 부모로 부터 상속되어야 한다.
        on: false,
    }),
    created() {
        this.boxId = this.$route.params.boxId;
        this.initialize();
    },
    methods : {
        // swiperInit: function() {
        //     this.activeTab()
        // },
        // activeTab: function(swiper) {
        //     swiper
        // },

        initialize() {
            this.getRecipeBoxById(this.boxId);
            this.getRecipeBoxList();
            this.getRecipeRecipeBoxList(this.boxId);
        },
        async getRecipeBoxList() {            
            const response = await this.$api(
            `${this.$API_SERVER}/api/recipebox/mine`,
            "get"
            );
            if (response.status === this.HTTP_OK) {
                this.recipeBoxes = response.data;
            }
        },
        async getRecipeBoxById(id) {
            const response = await this.$api(
            `${this.$API_SERVER}/api/recipebox/${id}`,
            "get"
            );
            if (response.status === this.HTTP_OK) {
                this.selectedRecipeBox = response.data;
            }
        },
        async getRecipeRecipeBoxList(id) {
            this.recipeList = [];
            const response = await this.$api(
            `${this.$API_SERVER}/api/reciperecipebox/recipe`,
            "get",
            {box: id}
            );            
            if (response.status === this.HTTP_OK) {
                console.log(response.data);
                response.data.forEach( obj => {
                    this.recipeList.push({
                        title: obj.title,
                        subTitle: obj.subTitle,
                        new: obj.new,
                        score: obj.score,
                        timeTaken: obj.timeTaken,
                        period: obj.period,
                        recipeId: obj.recipeId,
                        contentsId: obj.contentsId,
                        fileId: obj.fileId,
                        file: obj.fileId?`${this.$API_SERVER}/file/download/thumbnail?fileId=${obj.fileId}`:this.mainPicture,
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
        setSelectedRecipe(id) {
            let index = this.selectedRecipeIds.findIndex(x => x === id);
            if(index>=0) {
                this.selectedRecipeIds.splice(index, 1);
            }else{
                this.selectedRecipeIds.push(id);
            }
        },
        selectRecipeBox(id) {
            console.log(`selectRecipeBox: ${id} boxId: ${this.boxId}`);
            location.href=`/recipebox/${id}`;
        },
        moveRecipeBox() {
            this.selectedRecipeIds.forEach(async (item, index, arr) => {
                this.tempBoxId.forEach(async (id, index, arr) => {     
                // console.log(`${this.selectedRecipeBox.id}?recipe=${item}&to=${id}`);           
                    const response = await this.$api(
                    `${this.$API_SERVER}/api/reciperecipebox/${this.selectedRecipeBox.id}`,
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
            `${this.$API_SERVER}/api/recipebox/${name}`,
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
                `${this.$API_SERVER}/api/recipebox/${id}`,
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
            this.selectedRecipeIds.forEach(async (item, index, arr) => {
                console.log(`${this.selectedRecipeBox.id}?recipe=${item}`);
                const response = await this.$api(
                `${this.$API_SERVER}/api/reciperecipebox/${this.selectedRecipeBox.id}`,
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
        callAllSelect() {
            console.log("All");
            this.selectedRecipeIds = []
            if(!this.on){
                this.recipeList.forEach((recipe, index, arr) => {
                    this.selectedRecipeIds.push(recipe.recipeId)
                });
            }
        },
        callMoveRecipe() {
            console.log("Move")
            this.moveStep = 1
        },
        callMoveRecipeBox(id){
            if(this.selectedRecipeIds.length == 0) return
            this.tempBoxId.push(id);
            this.tempMap = new Map();
            this.recipeList.forEach((recipe, index, arr) => {
                this.tempMap.set(index, recipe);
            });
            this.selectedRecipeIds.forEach((recipeId, index, arr) => {
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
            this.selectedRecipeIds.forEach((recipeId, index, arr) => {
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
                this.selectedRecipeIds = [];
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
                this.selectedRecipeIds = [];
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
        allSelected: {
            get: function() {
                this.recipeIds = []
                this.recipeList.slice(0,5).forEach((recipe) => {
                    this.recipeIds.push(recipe.recipeId)
                })
                return this.recipeIds.length === this.selectedRecipeIds.length;
            },
            set: function(e) {
                this.selectedRecipeIds = e ? this.recipeIds : [];
            }
        }
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

    watch: {
        swiperOption(newValue) {
            console.log('swiperOption:', newValue)
        },
        on(newValue){
            console.log("on: ", newValue)
        },
        selectedRecipeIds(newValue){
            console.log("selectedRecipeIds: ", newValue)
        },     
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
      min-width: 56px !important;overflow: hidden;
      padding: 0px 14px;
      line-height: 30px;
      text-align: center;
      border:#CBCBCB solid 1px; 
      color:#CBCBCB;
      border-radius:20px; 
      font-weight: 500; 
      display: inline-block; 
      padding: 2px 8px; 
      margin: 0 4px 0 0; 
      font-size: 14px;
    //   background: #f3f4f7;
    background: #fff;
      appearance: none;
      cursor: pointer;
    //   &[aria-selected="true"] { // FIXME: 왜 적용 안될까? hover 시 텍스트 색상 적용 안됨
    //     border: #FF9519 solid 1px; 
    //     color:#FF9519;
    //     font-weight: 600;
    //   }
    }
  }
}
// .swiper-container .swiper-wrapper .swiper-slide:hover {
//         border: #FF9519 solid 1px; 
//         color:#FF9519;
//         font-weight: 600;

// }
// .navbar ul {
//     list-style: none;
// }
// .contents ul {
//     list-style: none;
// }
// .contents li {
//     float: left;
//     margin: 1px 6px;
// }
</style>

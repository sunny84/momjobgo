d<template>
  <div>
    <main class="recipebox">
        <BoxListMenu :key="recipeId"></BoxListMenu>
        <!-- <BoxKeywordView :key="listView"></BoxKeywordView> -->
        <span hidden>{{ step }} {{ boxId }} {{ recipeId }} {{ recipeList }} ...... {{ recipes }} {{ selectedRecipeBoxIds }}</span>
        <div class="wrap-boxes">
            <div class="boxes">
                <swiper class="wrap_keywords" ref="filterSwiper" :options="swiperOption" role="tablist">                    
                    <swiper-slide role="tab" class="wrap_in">
                        <div class="keywords" 
                            :class="{on : step == 4}">
                            <button @click="setSelectedRecipeBox('all')">모든 레시피</button>
                        </div>
                    </swiper-slide>
                    <swiper-slide role="tab" 
                        v-for="(box, $index) in recipeBoxes"
                        :key="$index" class="wrap_in">
                        <div
                            class="keywords" 
                            :class="{on : step == 0 || step == 1 && boxId == box.id}" 
                            @click="setSelectedRecipeBox(box.id)">
                            <span>
                                {{box.name}}
                            </span>
                        </div>
                    </swiper-slide>
                    <swiper-slide role="tab" class="wrap_in">
                        <div class="keywords" 
                            :class="{on : step == 3}">
                            <button @click="isModalViewed=true; setSelectedRecipeBox('new')">{{ $t('button.addNewBox') }} +</button>
                        </div>
                    </swiper-slide>
                </swiper>
                <!-- ////<div class="wrapper">
                <div class="swiper-pagination" slot="pagination"></div>
                <div class="swiper-button-prev" slot="button-prev"></div>
                <div class="swiper-button-next" slot="button-next"></div>
                </div>//// -->
                <!-- <div>
                ////<infinite-loading @infinite="infiniteHandler"> -->
                <!-- <infinite-loading @infinite="getRecipeBoxList">
                    <div slot="no-more"><br/></div>
                </infinite-loading>
                </div> -->
            </div>
            <div>
                <span hidden>선택된 레시피박스: {{ selectedRecipeBox.name }}[{{ selectedRecipeBox.id }}]</span>
            </div>
        </div>
        <div v-if="step==0">
            <div v-for="(all, $index) in allBoxInfo" :key="$index">
            <div v-for="(boxes, $index) in all" :key="$index">
            <div v-for="(box, $index) in boxes" :key="$index">
            <div v-if="selectedRecipeBox.id == box.id">
                <div class="wrap_allnum fl">
                    <span class="dp-inline-block fl">{{$t("content.all")}} </span>
                    <span class="num">{{ box.recipe?box.recipe.length:0 }}</span>
                </div>
                <div class="btn btn-default edit fr">
                    <span class="padding-right-5" @click="callEdit">{{$t("button.edit")}}</span>
                </div>
                <!-- TODO: 이부분만 갱신하도록 수정 --> 
                <!-- TODO: 편집step==3부분도 변경 --> 
                <div class="wrap_recipes" v-if="box.recipe.length > 0">
                    <div class="alltitle">{{ box.name }}</div>
                    <div class="wrap_in" v-for="(recipe, $index) in box.recipe" :key="$index">
                    <router-link :to="'/recipedetail/'+recipe.recipeId">
                        <div class="photo fl"><img :src="getImgURL(recipe.mainImgId)"/></div>
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
                    <!-- ////<infinite-loading @infinite="getRecipeRecipeBoxList(boxId)">
                        <div slot="no-more"><br/></div>
                    </infinite-loading>//// -->
                    <!-- <div
                        type="button"
                        v-on:click="appendRecipes(false)"
                        :disabled="this.dataRcpFull === true"
                        :hidden="this.totRecipes == this.cntRecipes"
                        :class="{disabled : dataRcpFull}"
                    >
                    더보기 ({{cntRecipes}}/{{totRecipes}})
                    </div> -->
                </div>
            </div>
            </div>
            </div>
            </div>
        </div>
        <!-- <div v-if="step===1 || step===0" > -->
        <div v-if="step==1">
            <!-- <BoxReipeView :boxId="selectedRecipeBox.id"></BoxReipeView> -->
            <div class="wrap_allnum fl">
                <span class="dp-inline-block fl">{{$t("content.all")}} </span>
                <span class="num">{{ recipeList?recipeList.length:0 }}</span>
            </div>
            <div class="btn btn-default edit fr">
                <span class="padding-right-5" @click="callEdit">{{$t("button.edit")}}</span>
            </div>
            <div class="wrap_recipes" v-if="recipeList.length > 0">
                <div class="alltitle">{{ selectedRecipeBox.name }}</div>
                <div class="wrap_in" v-for="(recipe, index) in recipes" :key="index">
                <router-link :to="'/recipedetail/'+recipe.recipeId">
                    <span hidden>{{recipe.mainImgId}} {{recipeList.length}} {{ recipes.length}}</span>
                    <div class="photo fl"><img :src="getImgURL(recipe.mainImgId)"/></div>
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
                <!-- ////<infinite-loading @infinite="getRecipeRecipeBoxList(boxId)">
                    <div slot="no-more"><br/></div>
                 </infinite-loading>//// -->
                <!-- <div
                    type="button"
                    v-on:click="appendRecipes(false)"
                    :disabled="this.dataRcpFull === true"
                    :hidden="this.totRecipes == this.cntRecipes"
                    :class="{disabled : dataRcpFull}"
                >
                더보기 ({{cntRecipes}}/{{totRecipes}})
                </div> -->
            </div>
        </div>
        <div v-if="step===2">
            <div class="wrap_allnum fl contents">
                <div class="fl" v-on:click="on = !on">
                    <span class="select dp-inline-block fl margin-right-5" 
                        :class="{on : on}" 
                        @click="callAllSelect()"
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
                <!-- <div class="wrap_in" v-for="(recipe, index) in recipeList.slice(0,5)" :key="index"> -->
                    <div class="wrap_in" v-for="(recipe, $index) in recipes" :key="$index">
                    <div class="photo fl">
                        <img :src="getImgURL(recipe.mainImgId)"/>
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
                    <span class="select fl margin-right-5"></span>
                    <span class="dp-inline-block fl">{{$t("content.select")}} {{ selectedRecipeIds.length }}/{{ recipeList.length }}</span>
                    </div>
                    <div class="fr b color-grey2 margin-bottom-5" style="margin-top: -28px;" @click="isModalViewed=true">{{ $t('button.addNewBox') }} +</div>
                </div>
                <div class="wrap_select" id="more_list">
                    <ul v-for="(box, index) in boxes" :key="index">
                        <li class="menu fl"
                            :class="{on : tempBoxId == box.id}"
                            @click="callMoveRecipeBox(box.id)">
                            {{ box.name }}
                            <button hidden v-on:click="callDeleteBox(box.id)">X</button>
                        </li>
                    </ul>
                    <div
                        class="arr_right" 
                        type="button"
                        v-on:click="appendBoxes()"
                        :disabled="this.dataFull === true"
                        :class="{disabled : dataFull}"
                    >
                    더보기 ({{cntBoxes}}/{{totBoxes}})
                    </div>
                </div>
                <div class="btn btn-default" style="width:112px;" @click="cancel">{{$t("button.cancel")}}</div>
            </div>
        </div>
            <ModalView v-if="isModalViewed" @close-modal="isModalViewed=false">
            <div slot="body">
            <!-- <fieldset class="wrap_in"> -->
                <!-- <legend>새 박스 추가</legend> -->
                <form v-on:submit="onSubmitForm">
                    <h1><label class="title" for="newBox">박스 추가</label></h1><br/>
                    <input class="width90" type="text" v-model="newBox" maxlength="10" placeholder="박스 명(최대 13자)">
                <div>
                    <label id="result-label" hidden for="result"></label><br/>
                    <div class="btn btn-default fl margin-left-30 margin-bottom-40" @click="isModalViewed=false">{{$t("button.cancel")}}</div>
                    <div class="btn btn-default fl margin-left-10"><button style="none">저장</button></div>
                </div>
                </form>
            <!-- </fieldset> -->
            </div>
            </ModalView>
        <!-- <div class="wrap_newbox" id="new-box" v-if="step===3">
        </div> -->
        <div v-if="step===4">
            <!-- <BoxReipeView :boxId="boxId"></BoxReipeView> -->
            <div class="wrap_allnum fl">
                <span class="dp-inline-block fl">{{$t("content.all")}} </span>
                <span class="num" v-for="(box, $index) in allBoxInfo" :key="$index">{{ box.recipeCnt }}</span>
            </div>
            <div class="btn btn-default edit disabled fr">
                <span class="padding-right-5" @click="callEdit">{{$t("button.edit")}}</span>
            </div>
            <!-- TODO: 이부분을 갱신해서 가져오도록 수정 -->
            <div class="wrap_recipes">
                <div v-for="(box, $index) in recipeBoxes" :key="$index">
                <div class="alltitle" v-if="box.isDefault===false && box.recipe && box.recipe.length > 0">{{ box.name }}</div>
                <div v-else-if="box.isDefault===true"><br/></div>
                <div v-else-if="box.recipe?false:true"></div><!-- 빈 폴더(박스) 비활성화 -->
                <div v-else></div>
                <div class="wrap_in" v-for="recipe in box.recipe" :key="recipe.id">
                <router-link :to="'/recipedetail/'+recipe.recipeId">
                    <div class="photo fl"><img :src="getImgURL(recipe.mainImgId)"/></div>
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
                <!-- <div
                    type="button"
                    v-on:click="appendRecipes(true)"
                    :disabled="this.dataRcpFull === true"
                    :hidden="this.totRecipes == this.cntRecipes"
                    :class="{disabled : dataRcpFull}"
                >
                더보기 ({{cntRecipes}}/{{totRecipes}})
                </div> -->
                <!--////<infinite-loading @infinite="getRecipeBoxList">
                    <div slot="no-more"><br/></div>
                </infinite-loading>//// -->
            </div>
        </div>
    </main>
    </div>
</template>
<script>
import emptyImg from '@/assets/emptyImg.png'
import { swiper, swiperSlide } from "vue-awesome-swiper";
import "swiper/dist/css/swiper.min.css";
import { mapGetters, mapActions } from "vuex";
import InfiniteLoading from 'vue-infinite-loading';
import ModalView from '@/components/ModalView.vue';
import BoxListMenu from "../components/BoxListMenu.vue";

export default {
    name : "RecipeBoxView",
    data: ()=>({
        swiperOption: {
            slidesPerView: 3.5,   // 'auto'
            spaceBetween: 10,   // swiper-slide 사이의 간격 지정
            slidesOffsetBefore: 10, // slidesOffsetBefore는 첫번째 슬라이드의 시작점에 대한 변경할 때 사용
            slidesOffsetAfter: 10, // slidesOffsetAfter는 마지막 슬라이드 시작점 + 마지막 슬라이드 너비에 해당하는 위치의 변경이 필요할 때 사용
            freeMode: true, // freeMode를 사용시 스크롤하는 느낌으로 구현 가능
            centerInsufficientSlides: false, // 컨텐츠의 수량에 따라 중앙정렬 여부를 결정함
            slideToClickedSlide: true,
        },
        recipeBoxes : [],   // 전체 박스 목록 데이터
        selectedRecipeBox : [],
        selectedRecipeBoxIds : [],
        /* 
            step=0 레시피 박스 화면 RecipeBoxListView.vue 로 분리
            step=1 레시시 박스 상세 화면
            step=2 레시피 박스 편집 화면
            step=3 새 레시피 박스 입력 화면
        */
        step : 0,
        /* 
            moveStep=0 레시피 박스 편집 화면
            moveStep=1 레시시 박스 편집 > 이동 화면
        */
        moveStep : 0,
        recipeList : [],    // 전체 레시피 목록 데이터
        mainPicture : '',
        selectedRecipeIds : [],
        recipeIds : [],
        tempMap : {},
        boxName : '기본박스',
        boxId : 0,
        newBox : "",
        edit : '',
        // tempBoxId : [],
        tempBoxId : 0,
        rcpId: 0, // TODO: 부모로 부터 상속되어야 한다.
        on: false,
        // 편집 > 박스이동 더보기
        // boxesAll: {},    // 전체 데이터
        boxes: {},       // 화면에 노출되는 데이터
        totBoxes: 0,     // 전체 데이터 수
        cntBoxes: 3,     // 화면에 노출할 데이터 수 (초기 세팅 = 3) 기본박스 + 1
        dataFull: false,// 전체 데이터보다 많은 데이터 호출 여부
        // 담긴 레시피 목록 더보기
        // recipesAll: {},    // 전체 레시피 목록 데이터
        recipes: {},       // 화면에 노출되는 데이터
        totRecipes: 0,     // 전체 데이터 수
        cntRecipes: 5,     // 화면에 노출할 데이터 수 (초기 세팅 = 5)
        dataRcpFull: false,// 전체 데이터보다 많은 데이터 호출 여부
        page : 0,
        allBoxInfo: [],
        isModalViewed: false,
    }),
    
    components: {
    swiper,
    swiperSlide,
    InfiniteLoading,
    ModalView,
    BoxListMenu
},    

    computed: {
        ...mapGetters("box", ["recipeId"]),

        swiper() {
            console.log("computed:swiper");
            return this.$refs.filterSwiper.swiper;
        },

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
        },
    },

    created() {
        this.boxId = this.$route.params.boxId
        this.initialize();
    },

    mounted () {
        console.log("mounted");
    },

    watch: {
        boxId(boxId) {          
            this.getRecipeBoxById(boxId)
            this.getRecipeRecipeBoxList(boxId)
            // makeRecipeList()
        }
    },

    methods : {
        // ...mapActions('box', ['setStep','setBoxId', 'setAllBox', 'setSelectedRBox']),
        initialize(){
            this.getRecipeBoxAll()
            if(this.boxId == 'all'){ 
                this.step = 4
            } else{
                this.getRecipeBoxById(this.boxId)
                // this.getRecipeRecipeBoxList(this.boxId)
            }
        },
        makeRecipeList(){
            this.recipeBoxes.forEach(box => {
                if(box.recipe){
                    if(box.id == this.boxId){
                        this.recipeList.push({
                            title: recipe.title,
                            subTitle: recipe.subTitle,
                            new: recipe.new,
                            score: recipe.score,
                            timeTaken: recipe.timeTaken,
                            period: recipe.period,
                            recipeId: recipe.recipeId,
                            contentsId: recipe.contentsId,
                            mainImgId: recipe.mainImgId,
                            boxName: this.selectedRecipeBox.name?this.selectedRecipeBox.name:"기본박스",
                            boxId: this.selectedRecipeBox.id,
                        })
                    }
                }
                console.log("====", this.recipeList)
            });
            
                        
        },
        // recipeBoxes
        async getRecipeBoxAll() {
            let params = '';
                // params += `?page=${this.page}`;
                // params += '&sort=createdAt,DESC';
            const response = await this.$api(`${this.$API_SERVER}/api/reciperecipebox/recipe/mine`+params, "get");
            if (response.status === this.HTTP_OK) {
                this.allBoxInfo = []
                this.recipeBoxes = []
                response.data.forEach(data => {
                    if(data.id){
                        this.recipeBoxes.push(data)
                    }
                });

                console.log("getRecipeBoxAll:",this.recipeBoxes)

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
                this.bindBoxes()
                console.log(this.allBoxInfo)
            }
        },
        // selectedRecipeBox
        async getRecipeBoxById(id) {
            console.log("getRecipeBoxById:", id)
            const response = await this.$api(
            `${this.$API_SERVER}/api/recipebox/${id}`,
            "get"
            );
            if (response.status === this.HTTP_OK) {
                this.selectedRecipeBox = response.data;
            }
        },
        // recipeList
        async getRecipeRecipeBoxList($state) {    
            let params = `?page=${this.page}`;
                // params += '&sort=createdAt,DESC';
            console.log(params);
            this.recipeList = []; 
            const response = await this.$api(`${this.$API_SERVER}/api/reciperecipebox/recipe`+params, `get`, {box: $state.boxId});
            if (response.status === this.HTTP_OK) {
                if(response.data.length){
                // console.log(response.data);
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
                        mainImgId: obj.mainImgId,
                        mainImg: obj.mainImgId?`${this.$API_SERVER}/file/download/thumbnail?fileId=${obj.mainImgId}`:this.mainPicture,
                        boxName: this.selectedRecipeBox.name?this.selectedRecipeBox.name:"기본박스",
                        boxId: this.selectedRecipeBox.id,
                        commentsNumber : 66   // TODO: comments
                    })             
                });
                
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
            this.bindRecipes()
        },
        
        async getRecipeRecipeBoxList() {    
            let params = `?page=${this.page}`;
                // params += '&sort=createdAt,DESC';
            console.log(params);
            this.recipeList = []; 
            if(this.boxId == 'all' || this.boxId == 'new'){
                const response = await this.$api(`${this.$API_SERVER}/api/reciperecipebox/recipe/mine`+params, "get");
                if (response.status === this.HTTP_OK) {
                    if(response.data.length){
                        console.log(response.data);
                    }
                }
            }
            else{
                const response = await this.$api(`${this.$API_SERVER}/api/reciperecipebox/recipe`+params, `get`, {box: this.boxId});
            if (response.status === this.HTTP_OK) {
                if(response.data.length){
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
                        mainImgId: obj.mainImgId,
                        mainImg: obj.mainImgId?`${this.$API_SERVER}/file/download/thumbnail?fileId=${obj.mainImgId}`:this.mainPicture,
                        boxName: this.selectedRecipeBox.name?this.selectedRecipeBox.name:"기본박스",
                        boxId: this.selectedRecipeBox.id,
                        commentsNumber : 66   // TODO: comments
                    })             
                });   
                this.bindRecipes()
                }
            }
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
        setSelectedRecipeBox(id) {
            if(id == 'all'){
                this.step = 4
            } else if(id == 'new'){
                this.step = 3
            } else{
                this.step = 1
                this.boxId = id
                let index = this.selectedRecipeBoxIds.findIndex(x => x === id);
                if(index>=0) {
                    this.selectedRecipeBoxIds.splice(index, 1);
                }else{
                    this.selectedRecipeBoxIds = []
                    this.selectedRecipeBoxIds.push(id);
                }
                this.selectRecipeBox(id)
            }
        },
        selectRecipeBox(id) {
            console.log(`selectRecipeBox: ${id} boxId: ${this.boxId}`);
        },
        moveRecipeBox() {
            this.selectedRecipeIds.forEach(async (item, index, arr) => {
                // 여러 박스에 담기 기능
                // this.tempBoxId.forEach(async (id, index, arr) => {                
                //     const response = await this.$api(
                //     `${this.$API_SERVER}/api/reciperecipebox/${this.selectedRecipeBox.id}`,
                //     "post",
                //     {
                //         recipe: item,
                //         to: id
                //     }
                //     );
                //     if (response.status === this.HTTP_OK) {
                //         console.log("moveRecipeBox:", response.data);
                //     }
                // });
                // 단일 박스에 담기
                const response = await this.$api(
                `${this.$API_SERVER}/api/reciperecipebox/${this.selectedRecipeBox.id}`,
                "post",
                {
                    recipe: item,
                    to: this.tempBoxId
                }
                );
                if (response.status === this.HTTP_OK) {
                    console.log("moveRecipeBox:", response.data);
                }
            });
            //this.getRecipeBoxById(this.boxId)
            //this.getRecipeRecipeBoxList(this.boxId);
        },
        addNewBoxPage() {
            console.log("addNewBoxPage step=2")
            this.step = 3
            this.boxId = 0
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
            this.isModalViewed = false
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
            // this.tempBoxId.forEach( async(id, index, arr) => {
                console.log("deleteBoxId: "+this.tempBoxId);
                const response = await this.$api(
                `${this.$API_SERVER}/api/recipebox/${this.tempBoxId}`,
                "delete"
                );
                if (response.status === this.HTTP_OK) {
                    console.log(response.data);
                }
            // })
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
        callAllSelect(){
            this.selectedRecipeIds = []
            if(!this.on){
                this.recipes.forEach((recipe, index, arr) => {
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
            // this.tempBoxId.push(id);
            this.tempBoxId = id;
            this.tempMap = new Map();
            this.recipes.forEach((recipe, index, arr) => {
                this.tempMap.set(index, recipe);
            });
            this.selectedRecipeIds.forEach((recipeId, index, arr) => {
                this.recipes.forEach((recipe, index, arr) => {
                    if(recipeId == recipe.recipeId){
                        this.$delete(arr, index)
                    }
                });
            });
            this.edit = 'move';
        },
        callDeleteBox(id){
            // this.tempBoxId.push(id);
            this.tempBoxId = id;
            this.tempMap = new Map();
            this.recipes.forEach((box, index, arr) => {
                this.tempMap.set(index, box);
            });
            this.recipes.forEach((box, index, arr) => {
                if(id == box.id){
                    this.$delete(arr, index)
                }
            });
            this.edit = 'deleteBox';
        },
        callDeleteRecipe(){
            this.tempMap = new Map();
            // this.recipeList.forEach((recipe, index, arr) => {
            this.recipes.forEach((recipe, index, arr) => {
                this.tempMap.set(index, recipe);
            });
            // view 화면에서 숨기기
            this.selectedRecipeIds.forEach((recipeId, index, arr) => {
                this.recipes.forEach((recipe, index, arr) => {
                // this.recipeList.forEach((recipe, index, arr) => {
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
                this.recipes = [];
                for(let i=0;i<this.tempMap.size;i++){
                    this.recipes.push(this.tempMap.get(i))
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
                this.recipes = [];
                for(let i=0;i<this.tempMap.size;i++){
                    this.recipes.push(this.tempMap.get(i))
                };
            }
            this.step = 1
            this.moveStep = 0
            this.cntBoxes = 3;
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
            this.cntBoxes = 3;
            this.$router.push('/recipebox/'+this.boxId)
        },
        bindBoxes() {
            console.log(this.recipeBoxes.length)
            this.totBoxes = this.recipeBoxes.length;
            let data = []
            for(var i=0;i<this.cntBoxes;i++){
                if(this.recipeBoxes[i])
                    data.push(this.recipeBoxes[i])
                else
                    this.cntBoxes = i
            }
            this.boxes = data;
            console.log("bindBoxes:", this.totBoxes, this.boxes)
        },
        appendBoxes() {
            if(this.cntBoxes < this.totBoxes){
                this.cntBoxes += 3
                let data = []
                for(var i=0;i<this.cntBoxes;i++){
                    if(this.recipeBoxes[i])
                        data.push(this.recipeBoxes[i])
                    else
                        this.cntBoxes = i
                }
                this.boxes = data
            }else{
                this.dataFull = true
                alert('List items are fully loaded!')
            }
            console.log("appendBoxes:", this.totBoxes, this.boxes)
        },
        bindRecipes() {
            console.log("this.bindRecipes", this.recipeList)
            this.totRecipes = this.recipeList.length;
            let data = []
            let idx = 0
            // FIXME: this.recipeList[i] 값이 있으나 체크 못하는 경우가 있어서 forEach로 변경함
            this.recipeList.forEach((recipe, index, array) => {
                if(recipe){
                    data.push(recipe)
                    idx = index + 1
                }
                if(index>=this.cntRecipes)
                    return true;
            })
            this.cntRecipes = idx;
            this.recipes = data;
        },
        appendRecipes(all) {
            if(this.cntRecipes < this.totRecipes){
                this.cntRecipes += 5
                let data = []
                for(var i=0;i<this.cntRecipes;i++){
                    if(all){
                        // TODO: 복잡하네...
                        // recipeList 형식에 맞게 모든 레시피 목록을 따로 만들어 보낼까? 
                        // recipeList 형식에 box.name 필요. 최신순 정렬 필요
                        //this.recipeBoxes
                        // data.push(this.recipeList[i])
                    }
                    if(this.recipeList[i]){
                        data.push(this.recipeList[i])
                    }else{
                        this.cntRecipes = i
                    }
                }
                this.recipes = data
            }else{
                this.dataRcpFull = true
                alert('List items are fully loaded!')
            }
        }, 
        recipeAll() {
            this.step = 4
            this.boxId = 0
        },

        getImgURL(id) {
            const url = `${this.$API_SERVER}/file/download/thumbnail?fileId=` + id;
            console.log(url);
            return url
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
</style>

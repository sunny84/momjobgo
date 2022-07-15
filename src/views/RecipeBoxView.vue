<template>
    <div>
        <div>
        <!--HEADER-->
            <h4>{{ $t("title.recipeBox") }}</h4>
            <div class="menu">
                <ul class="submenu">
                    <li><a href="/myrecipe">{{ $t("menu.myRecipe") }}</a></li>
                    <li><a href="/recipeboxlist">{{ $t("menu.savedRecipe") }}</a></li>
                    <li><a href="#">{{ $t("menu.historyRecipe") }}</a></li>
                </ul>
                <br/>
            </div>
            <!--레시피박스 페이지에서 비활성화, 레시피 박스 선택시 활성화-->
            <div class="boxes"><!-- v-if="step===1"-->
                <ul v-for="(item, index) in recipeBoxes" :key="index">
                    <li v-if="item.isDefault == true" @click="selectRecipeBox(item.id)">{{item.name}}{{item.id}}</li>
                </ul>
                <ul v-for="(item, index) in recipeBoxes" :key="`o-${index}`">
                    <li v-if="item.isDefault == false" @click="selectRecipeBox(item.id)">{{item.name}}{{item.id}}</li>
                    <!-- <li v-else-if="item.isDefault == null" @click="select(item.id)">
                        {{item.name}}{{item.id}}
                    </li> -->
                </ul>
                <ul>
                    <li>
                        <confirm-input 
                            :text="'+ '+$t('button.addNewBox')"
                            :title="$t('button.addNewBox')"
                            :value="boxName"
                            :callback="text => addNewBox(text)"
                        />
                    </li>
                </ul>
                <br/>
                <span>선택된 레시피박스: {{ selectedRecipeBox.name }}[{{ selectedRecipeBox.id }}]</span>
            </div>
        <!--CONTENTS-->
            <div class="contents" v-if="step===1">
                {{$t("content.all")}} {{ list.length }}
                <button @click="callEdit">{{$t("button.edit")}}</button>
                <table>
                    <thead>
                        <td></td>
                        <td></td>
                    </thead>
                    <!-- DB로 부터 가져와야 레시피 목록
                        최대 5개의 레시피 목록을 가져온다. -->
                    <tr v-for="(item, index) in list" :key="index">
                        <td>
                            <p>
                                <img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg">
                            </p>
                        </td>
                        <td>
                            <router-link :to="item.title"><!--TODO: a herf="레시피 박스 상세 페이지로 이동"-->
                            {{item.title}} <br/>
                            {{item.subTitle}} <br/>
                            <ul v-for="(period, idx) in $t('option.period')" :key="idx">
                                <li v-if="item.period == idx">{{period}} {{item.boxName}}|{{item.recipeId}}|{{item.boxId}}</li>
                            </ul>
                            </router-link>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="contents" v-if="step===2">
                {{$t("content.all")}} {{ list.length }}
                <button @click="cancel">{{$t("button.cancel")}}</button>&nbsp;
                <button @click="done">{{$t("button.done")}}</button>
                <table>
                    <thead>
                        <td></td>
                        <td></td>
                        <td></td>
                    </thead>
                    <!-- DB로 부터 가져와야 레시피 목록
                        최대 5개의 레시피 목록을 가져온다. -->
                    <tr v-for="(item, index) in list" :key="index">
                        <td>
                            <p>
                                <img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg">
                            </p>
                        </td>
                        <td>
                            <router-link :to="item.title"><!--TODO: a herf="레시피 박스 상세 페이지로 이동"-->
                            {{item.title}} <br/>
                            {{item.subTitle}} <br/>
                            <ul v-for="(period, idx) in $t('option.period')" :key="idx">
                                <li v-if="item.period == idx">{{period}} {{item.boxName}} {{item.recipeId}} {{item.boxId}}</li>
                            </ul>
                            </router-link>
                        </td>
                        <td>
                            <input
                                type="checkbox"
                                id=index
                                :value=item.recipeId 
                                v-model="checkedRecipeIds"
                            >
                            <!-- <label for=index>{{item.title}}</label> -->
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <span>체크한 이름: {{ checkedRecipeIds }}</span>
                        </td>
                    </tr>
                </table>

                <div class="moveBox" v-if="moveStep===0">
                    <button @click="moveRecipe">{{$t("button.move")}}</button>&nbsp;
                    <button @click="deleteRecipe">{{$t("button.delete")}}</button>
                </div>
                <div class="moveBox" v-if="moveStep===1">
                    <div class="moveBoxHeader">
                        <ul>
                            <li>{{$t("content.moveBox")}}</li>
                            <li>
                                <confirm-input 
                                :text="'+ '+$t('button.addNewBox')"
                                :title="$t('button.addNewBox')"
                                :value="boxName"
                                :callback="text => addNewBox(text)"
                                />
                            </li>
                        </ul>
                    </div>
                    <div class="moveBoxBody">
                        <ul v-for="(item, index) in recipeBoxes" :key="index">
                            <li v-if="item.isDefault == true" @click="moveRecipeBox(item.id)">
                                <button v-on:click="deleteBoxId(item.id)">X</button>
                                <p>
                                    <img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg">
                                </p>
                                {{item.name}}
                            </li>
                        </ul>
                        <ul v-for="(item, index) in recipeBoxes" :key="`o-${index}`">
                            <li v-if="item.isDefault == false" @click="moveRecipeBox(item.id)">
                                <button v-on:click="deleteBoxId(item.id)">X</button>
                                <p>
                                    <img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg">
                                </p>
                                {{item.name}}
                            </li>
                            <li v-else-if="item.isDefault == null" @click="moveRecipeBox(item.id)">
                                <button v-on:click="deleteBoxId(item.id)">X</button>
                                <p>
                                    <img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg">
                                </p>
                                {{item.name}}
                            </li>
                        </ul>
                    </div>
                    <div class="moveBoxFooter">
                        <button @click="cancelMove">{{$t("button.cancel")}}</button>
                    </div>
                </div>
            </div>
        <!--FOOTER-->
        </div>
    </div>
</template>
<script>
import axios from "axios"
import emptyImg from '@/assets/emptyImg.png'
import ConfirmInput from 'vue-confirm-input'

export default {
    name : "RecipeBoxView",
    data: ()=>({
        name : "",
        recipes : 0,
        recipeBoxes : [],
        selectedRecipeBox : [],
        /* 
            step=0 레시피 박스 화면
            step=1 레시시 박스 상세 화면
            step=2 레시피 박스 편집 화면
        */
        step : 2,
        /* 
            moveStep=0 레시피 박스 편집 화면
            moveStep=1 레시시 박스 편집 > 이동 화면
        */
        moveStep : 0,
        list : [],
        recipe : [],
        contents : [],
        score : [],
        timeTaken : [],
        mainPicture : '',
        boxList : [],
        checkedRecipeIds : [],
        boxName: '기본박스',
    }),

    components: {
        ConfirmInput
    },

    created() {
        console.log("this.$route.params.boxId", this.$route.params.boxId);

        this.initialize();
    },

    methods : {
        initialize() {
            this.callRecipeBox(this.boxName);
            this.callContents();
        },
        async callRecipeBox() {            
            const response = await this.$api(
            `http://localhost:8090/api/recipebox/all`,
            "get"
            );
            if (response.status === this.HTTP_OK) {
                this.recipeBoxes = response.data;
                this.selectedRecipeBox = this.recipeBoxes;
            }
        },
        async callRecipeBoxById(id) {
            const response = await this.$api(
            `http://localhost:8090/api/recipebox/${id}`,
            "get"
            );
            if (response.status === this.HTTP_OK) {
                this.selectedRecipeBox = response.data;
            }
        },
        async callContents() {
            // TODO: 접속자 컨텐츠만 가져오도록 API 추가 필요
            const response = await this.$api(
            // `http://localhost:8090/api/contents`,
            `http://localhost:8090/contents`,
            "get"
            );
            if (response.status === this.HTTP_OK) {
                this.contents = response.data;
            }

            // console.log(`contents length : ${this.contents.length}`);
            if(this.contents.length > 0)
            {
                var i = 0;
                this.list = [];
                while(i < this.contents.length ){
                    // console.log(i,"회");
                    const response1 = await this.$api(
                    `http://localhost:8090/Recipe/contents=${this.contents[i].id}`,
                    "get"
                    );
                    if (response1.status === this.HTTP_OK) {
                        this.recipe = response1.data;
                    }
                    // TODO: 동작 오류
                    // const response2 = await this.$api(
                    // `http://localhost:8090/score/recipe/${this.recipe[0].id}`,
                    // "get",
                    // );
                    // if (response2.status === this.HTTP_OK) {
                    //     this.score[i] = response2.data.score;
                    // }
                    this.score[i] = 5;//response2.data.score;

                    const response3 = await this.$api(
                    `http://localhost:8090/time-taken/${this.recipe[0].timeTakenId}`,
                    "get"
                    );
                    if (response3.status === this.HTTP_OK) {
                        this.timeTaken[i] = response3.data.score;
                    }

                    this.list.push({
                        title: this.contents[i].title,
                        subTitle: this.contents[i].subTitle,
                        score: this.score[i],
                        timeTaken: this.timeTaken[i],
                        period: this.recipe[0].period,
                        boxName: this.selectedRecipeBox.name?this.selectedRecipeBox.name:"기본박스",
                        recipeId: this.recipe[0].id,
                        boxId: this.selectedRecipeBox.id,
                        commentsNumber : 66   // TODO: comments
                    });

                    i = i + 1;          
                }
                // console.log(this.list);
            } 
        },
        setEmptyImg(e) {
            e.target.src=emptyImg;
        },
        selectRecipeBox(id) {
            console.log(`selectRecipeBox: ${id}`);
            this.callRecipeBoxById(id)
            this.callContents();
        },
        moveRecipeBox(id) {
            console.log(`moveRecipeBox: ${id}`);
            this.checkedRecipeIds.forEach(async (item, index, arr) => {
                console.log(`${this.selectedRecipeBox.id}?recipe=${item}&to=${id}`);
                const response = await this.$api(
                `http://localhost:8090/api/reciperecipebox/${this.selectedRecipeBox.id}`,   // TODO: API
                "post",
                {
                    recipe: item,
                    to: id
                }
                );
                if (response.status === this.HTTP_OK) {
                    // console.log("contents:", response.data);
                }
            });
            // this.callRecipeBoxById(id)
            // this.callContents();
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
        async deleteBoxId(id) {
            console.log("deleteBoxId: "+id);
            const response = await this.$api(
            `http://localhost:8090/api/recipebox/${id}`,
            "delete"
            );
            if (response.status === this.HTTP_OK) {
                // console.log("contents:", response.data);
            }
            this.initialize();
        },
        callEdit() {
            console.log("Edit");
            this.step = 2
        },
        moveRecipe() {
            console.log("Move")
            this.moveStep = 1
        },
        deleteRecipe() {
            console.log("Delete")            
            // 선택된 레시피박스의 checked 된 recipeId 를 삭제한다.
            this.checkedRecipeIds.forEach(async (item, index, arr) => {
                console.log(`${this.selectedRecipeBox.id}?recipe=${item}`);
                const response = await this.$api(
                `http://localhost:8090/api/reciperecipebox/${this.selectedRecipeBox.id}`,   // TODO: API
                "delete",
                { recipe: item }
                );
                if (response.status === this.HTTP_OK) {
                    // console.log("contents:", response.data);
                }
            });
        },
        cancel() {
            console.log("cancel")
            this.step = 1
        },
        done() {
            console.log("done")
            this.step = 1
        },
        cancelMove() {
            console.log("cancelMove")
            this.moveStep = 0
        },
    },
}
</script>
<style>
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
ul {
    list-style: none;
}
li {
    float: left;
}
/* .menu {
}
.menu ul{
    list-style: none;
}
.menu li {
    float: left;
}
.submenu {

}
.submenu ul{
    list-style: none;
}
.submenu li {
    float: left;
}
.boxes {

}
.boxes ul{
    list-style: none;
}
.boxes li {
    float: left;
} */
</style>

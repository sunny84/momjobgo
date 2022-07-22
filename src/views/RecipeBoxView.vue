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
                        <ul v-for="(recipe, index) in recipeBoxes" :key="index">
                            <li v-if="recipe.isDefault == true" @click="moveRecipeBox(recipe.id)">
                                <button v-on:click="deleteBoxId(recipe.id)">X</button>
                                <p>
                                    <img :src="recipe.file" width="200px" height="150px" @error="setEmptyImg">
                                </p>
                                {{recipe.name}}
                            </li>
                        </ul>
                        <ul v-for="(recipe, index) in recipeBoxes" :key="`o-${index}`">
                            <li v-if="recipe.isDefault == false" @click="moveRecipeBox(recipe.id)">
                                <button v-on:click="deleteBoxId(recipe.id)">X</button>
                                <p>
                                    <img :src="recipe.file" width="200px" height="150px" @error="setEmptyImg">
                                </p>
                                {{recipe.name}}
                            </li>
                            <li v-else-if="recipe.isDefault == null" @click="moveRecipeBox(recipe.id)">
                                <button v-on:click="deleteBoxId(recipe.id)">X</button>
                                <p>
                                    <img :src="recipe.file" width="200px" height="150px" @error="setEmptyImg">
                                </p>
                                {{recipe.name}}
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
import emptyImg from '@/assets/emptyImg.png'
import ConfirmInput from 'vue-confirm-input'

export default {
    name : "RecipeBoxView",
    data: ()=>({
        recipeBoxes : [],
        selectedRecipeBox : [],
        /* 
            step=0 레시피 박스 화면 RecipeBoxListView.vue 로 분리
            step=1 레시시 박스 상세 화면
            step=2 레시피 박스 편집 화면
        */
        step : 2,
        /* 
            moveStep=0 레시피 박스 편집 화면
            moveStep=1 레시시 박스 편집 > 이동 화면
        */
        moveStep : 0,
        recipeList : [],
        mainPicture : '',
        checkedRecipeIds : [],
        boxName: '기본박스',
        boxId: 0
    }),

    components: {
        ConfirmInput
    },

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
                        file: obj.fileId?`http://localhost:8090/file/download?fileId=${obj.fileId}`:this.mainPicture,
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
            this.getRecipeBoxById(id)
            this.getRecipeRecipeBoxList(id);
        },
        moveRecipeBox(id) {
            console.log(`moveRecipeBox: ${id}`);
            this.checkedRecipeIds.forEach(async (item, index, arr) => {
                console.log(`${this.selectedRecipeBox.id}?recipe=${item}&to=${id}`);
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
            //this.getRecipeBoxById(this.boxId)
            this.getRecipeRecipeBoxList(this.boxId);
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
                console.log(response.data);
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
        cancel() {
            console.log("cancel")
            this.step = 1
        },
        done() {
            console.log("done")
            this.step = 1
            this.initialize();
        },
        cancelMove() {
            console.log("cancelMove")
            this.moveStep = 0
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

<template>
    <div>
        <div v-if="step===0">
            <h4>{{ $t("title.writeRecipe") }}</h4>
            <p>{{ step +1}}</p>
            <p>{{ $t("description.writeRecipe")}}</p>
            <p>{{ $t("content.step")}}
                <select v-model="period">
                    <option v-for="(period, idx) in $t('option.period')" :value="idx" :key="idx">{{period}}</option>
                </select>
            </p>
            <p>{{$t("content.timeTaken")}}</p>
            <p><select v-model="timeTaken">
                    <option v-for="(time, idx) in $t('option.timeTaken')" :value="idx" :key="idx">{{time}}</option>
                </select></p>
        </div>
        <div v-else-if="step===1">
            <h4>{{ $t("title.writeRecipe") }}</h4>
            <p>{{ step +1}}</p>
            <p>{{ $t("description.ingredient")}}</p>
            <!-- DB에서 받아온 것을 어떻게 translate 시킬 것인지 결정 후 구현
                1. value=INGREDIENT의 id
                2. key를  translate 시켜서 label로
            -->
            <!-- start of test -->
            <div v-for="(category, idx) in ing_data" :key="idx">
                <p v-show="category.id!==1">{{category.name}}</p>
                <p>
                    <!-- <span v-for="(ing_single, ing_idx) in category.ingredientEntities" :key="ing_idx">
                        {{ing_single.id}}==={{ing_single.key}}<br>

                    </span> -->
                    <input v-for="(ing_single, ing_idx) in category.ingredientEntities" :key="ing_idx" type="checkbox" v-model="selectedIngredients" :value="ing_single.id">{{ing_single.key}}</input>
                </p>
            </div>
            <!-- end of test -->
            <p>{{ $t("content.choiceOfIngredient")}}
                <div>
                    <p>
                        <input type="checkbox" v-model="selectedIngredients" value="2">{{$t("ingredient.GLUTINOUS_RICE")}}</input>
                        <input type="checkbox" v-model="selectedIngredients" value="3">{{$t("ingredient.PEA")}}</input><br>
                        <input type="checkbox" v-model="selectedIngredients" value="4">{{$t("ingredient.CORN")}}</input>
                        <input type="checkbox" v-model="selectedIngredients" value="5">{{$t("ingredient.BROCCOLI")}}</input>
                        <input type="checkbox" v-model="selectedIngredients" value="6">{{$t("ingredient.CHINESE_CABBAGE")}}</input><br>
                        <input type="checkbox" v-model="selectedIngredients" value="7">{{$t("ingredient.WHITE_RADISH")}}</input>
                        <input type="checkbox" v-model="selectedIngredients" value="8">{{$t("ingredient.PAPRIKA")}}</input>
                        <input type="checkbox" v-model="selectedIngredients" value="9">{{$t("ingredient.GREEN_PUMPKIN")}}</input><br>
                        <input type="checkbox" v-model="selectedIngredients" value="10">{{$t("ingredient.LEAF_BEET")}}</input>
                        <input type="checkbox" v-model="selectedIngredients" value="11">{{$t("ingredient.CURLED_MALLOW")}}</input>
                        <input type="checkbox" v-model="selectedIngredients" value="12">{{$t("ingredient.MUSHROOM")}}</input><br>
                        <input type="checkbox" v-model="selectedIngredients" value="13">{{$t("ingredient.EGGPLANT")}}</input>
                        <input type="checkbox" v-model="selectedIngredients" value="14">{{$t("ingredient.CUCUMBER")}}</input>
                        <input type="checkbox" v-model="selectedIngredients" value="15">{{$t("ingredient.PAK_CHOI")}}</input>
                    </p>
                    <p>{{$t("ing_category.rootAndTuberCrops")}}</p>
                    <input type="checkbox" v-model="selectedIngredients" value="16">{{$t("ingredient.SWEET_PUMPKIN")}}</input>
                    <input type="checkbox" v-model="selectedIngredients" value="17">{{$t("ingredient.POTATO")}}</input>
                    <input type="checkbox" v-model="selectedIngredients" value="18">{{$t("ingredient.SWEET_POTATO")}}</input>

                    <p>{{$t("ing_category.blockIronAbsorption")}}</p>
                    <input type="checkbox" v-model="selectedIngredients" value="19">{{$t("ingredient.SPINACH")}}</input>
                    <input type="checkbox" v-model="selectedIngredients" value="20">{{$t("ingredient.CARROT")}}</input>

                    <p>{{$t("ing_category.fruits")}}</p>
                    <input type="checkbox" v-model="selectedIngredients" value="21">{{$t("ingredient.APPLE")}}</input>
                    <input type="checkbox" v-model="selectedIngredients" value="22">{{$t("ingredient.PEAR")}}</input>
                    <input type="checkbox" v-model="selectedIngredients" value="23">{{$t("ingredient.BANANA")}}</input>

                    <p>{{$t("ing_category.animalProtein")}}</p>
                    <input type="checkbox" v-model="selectedIngredients" value="24">{{$t("ingredient.BEEF")}}</input>
                    <input type="checkbox" v-model="selectedIngredients" value="25">{{$t("ingredient.CHICKEN")}}</input>
                    <input type="checkbox" v-model="selectedIngredients" value="26">{{$t("ingredient.WHITE_FISH")}}</input><br>
                    <input type="checkbox" v-model="selectedIngredients" value="27">{{$t("ingredient.YOLK")}}</input>
                    <input type="checkbox" v-model="selectedIngredients" value="28">{{$t("ingredient.TOFU")}}</input>

                    <input v-show="true" type="checkbox" v-model="selectedIngredients" value="1">물</input>
                </div>
            </p>
        </div>
        <div v-else-if="step===2">
            <h4>{{ $t("title.writeRecipe") }}</h4>
            <p>{{ step +1}}</p>
            <p>{{ $t("description.almostDone")}}</p>
            <p><input type="text" :placeholder="$t('content.title')" v-model="title"></input></p>
            <p><input type="text" :placeholder="$t('content.subTitle')" v-model="subTitle"></input></p>
            <p>{{$t("content.mainPicture")}}</p>
            <p><input type="file" id="mainpicture">+</input></p>
            <p>{{ $t("content.listOfIngredient") }}</p>
            <p><div v-for="(item, idx) of ingredients" :key="item">
                <span v-if="item!=='1'">{{item}}</span>
                <input type="number"></input>g
                </div>
                <div><span>{{$t("ingredient.WATER")}}</span>
                <input type="number"></input></div>
            </p>

        </div>
        <div>
            <p>
                <button v-show="this.step!==0" @click="changeStep(-1)">{{$t("button.back")}}</button> &nbsp;
                <button v-show="this.step!==3" @click="changeStep(+1)">{{$t("button.next")}}</button> &nbsp;
                <button v-show="this.step===3" @click="publish">{{$t("button.saveAndPreview")}}</button>
            </p>
            <p>{{$t("description.autoSave")}}</p>
        </div>
        
    </div>
</template>

<script>
import ing_data from '@/assets/ingredients.json'

export default {
    name : "RecipeWriteView",
    data: ()=>({
        ing_data,
        step : 1,
        period : 0,
        timeTaken : 0,
        selectedIngredients : [],
        title : "",
        subTitle : "",
    }),

    methods : {
        changeStep(arrow){
            this.step=this.step+arrow;
            //this.step=+arrow;
        },

        publish() {
            // save recipe into db
        }
    },
}
</script>

<style>

</style>
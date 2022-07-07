<template>
    <div>
        <h4>{{ $t("title.writeRecipe") }}</h4>
        <p>단계 : {{ step +1}}</p>
        <div v-if="step===0">
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
            <p>{{ $t("description.ingredient")}}</p>
            <p>{{ $t("content.choiceOfIngredient")}}</p>

            <div v-for="(category, idx) in ing_data" :key="idx" v-show="category.id!==6">
                <!-- HELP!!!!! -->
                <p v-show="category.id!==1">{{category.name}} - {{$t(("ing_category["+category.id+"]"))}}</p>
                <p>
                    <span v-for="(item, idx) in category.ingredientEntities" :key="item.id">
                        <input  id="ing_name"  type="checkbox" v-model="selectedIngredients" :value="item.id">
                        <!-- match i18n item & key value : OK-->
                        <label for="ing_name">{{$t("ingredient."+[item.key])}}</label>
                        <br v-if="category.id===1&&idx%3===1">
                        <br v-else-if="category.id!==1&&idx%3===2">
                    </span>
                </p>
            </div>

        </div>
        <div v-else-if="step===2">
            <p>{{ $t("description.almostDone")}}</p>
            <!-- title & subtitle -->
            <p><input type="text" :placeholder="$t('content.title')" v-model="title"></p>
            <p><input type="text" :placeholder="$t('content.subTitle')" v-model="subTitle"></p>
            <!-- main picture -->
            <p>{{$t("content.mainPicture")}}</p>
            <p><img :src="mainPicture" width="200px" height="150px" @error="setEmptyImg"></p>
            <p>
                <input type="file" id="mainPicture" class="pickpicture" accept="image/png, image/jpeg" @change="uploadImg"><label for="mainPicture">+</label></p>
            <!-- list and volume of ingredient -->
            <p>{{ $t("content.listOfIngredient") }}</p>
            <p v-for="item, idx in selectedIngredients" :key="item">
                <label for="ing_volume">{{item}}-{{$t("ingredient["+item+"]")}}  </label>
                <input type="number" id="ing_volume" v-model="ingredientVolume[idx]" />
                <span v-if="item!==1"> g</span>
                <span v-else> ml</span>
            </p>
            <!-- cooking order-->
            <p>{{$t("content.cookingOrder")}} <span @click="addOrder(this.cookingOrder.length+1)">+</span></p>
            <p><textarea id="cookingContents" width="140px" height="50px"></textarea>
                <input id="cookingPicture" type="file" accept="image/png, image/jpeg" @change="uploadImg" class="pickpicture"/><label for="cookingPicture" class="cookingOrderLabel">+</label></p>
            <!-- Tip -->
            <p>{{$t("content.tip")}} {{$t("content_description.option")}}</p>
            <p><input type="text" :placeholder="$t('content_description.tip')" @change="updateTip"/></p>
            <!-- Clip moive link -->
            <p>{{$t("content.movieClipUrl")}} {{$t("content_description.option")}}</p>
            <p><input type="url" :placeholder="$t('content_description.movieClipUrl')" pattern="https://.*"/></p>
            <!-- Youtube Full link -->
            <p>{{$t("content.youtubeUrl")}}  {{$t("content_description.option")}}</p>
            <p><input type="url" :placeholder="$t('content_description.youtubeUrl')" pattern="https://.*" /></p>
        </div>

        <div v-else-if="step===3">
            <p>{{ $t("description.finishAndSave")}}</p>
            <p><img :src="this.mainPictire" /></p>
            <p>{{this.title}}</p>
            <p>{{this.subtitle}}</p>
            <p>{{$t("option.timeTaken["+timeTaken+"]")}}</p>
        </div>

        <div>
            <p>
                <button v-show="this.step!==0" @click="validateByStep(-1)">{{$t("button.back")}}</button> &nbsp;
                <button v-show="this.step!==3" @click="validateByStep(+1)">{{$t("button.next")}}</button> &nbsp;
                <button v-show="this.step===3" @click="publish">{{$t("button.saveAndPreview")}}</button>
            </p>
            <p>{{$t("description.autoSave")}}</p>
        </div>
        
    </div>
</template>

<script>
import ing_data from '@/assets/ingredients.json'
import emptyImg from '@/assets/emptyImg.png'

export default {
    name : "RecipeWriteView",
    data: ()=>({
        ing_data,
        step : 0,
        period : 0,
        timeTaken : 0,
        selectedIngredients : [],
        ingredientVolume : [],
        ingredientsMap : [],
        cookingOrder : [],
        title : "",
        subTitle : "",
        mainPicture : "",
    }),

    methods : {
        createIngredientsMap() {
            for(var i=0; i<this.selectedIngredients.length; i++) {
                this.ingredientsMap.push({id: this.selectedIngredients[i], volume:this.ingredientVolume[i]});
            }
        },
        destroyIngredientsMap() {
            this.ingredientsMap = [];
        },
        validateByStep(arrow){
            // validate data by step then go next/back
            // push "WATER" for essential ingredient on step 1before go to step 2
            console.log(this.step);
            if(arrow>0 && this.step===1) {
                this.selectedIngredients.push(1);
            } else if(arrow<0 && this.step===2) {
                this.selectedIngredients.pop();
                this.ingredientVolume.pop();
            } else if(arrow>0 && this.step===2) {
                //make ingregientsMap
                this.createIngredientsMap();
            } else if(arrow<0 && this.step===3) {
                this.destroyIngredientsMap();
            }

            // then go
            this.changeStep(arrow);
        },
        changeStep(arrow){
            this.step=this.step+arrow;
            console.log("[changeStep]"+ this.step);
            //this.step=+arrow;
        },
        setEmptyImg(e) {
            e.target.src=emptyImg;
        },
        uploadImg(e){
            let files= e.target.files;
            if(e.target.id==="mainPicture") {
                this.mainPicture = URL.createObjectURL(files[0]);
            } else if(e.target.id==="cookingPicture") {
                //{contents_n0: 0, filename : '', contentType:'', contents:''}
                this.cookingOrder.push({contents_no:0, filnename:files[0].name, contentType:file[0].type, contents:""});
            }
            console.log(e);
        },
        addOrder(contents_no) {
            this.cookingOrder.push({contents_no:contents_no, filename:'', contentType:'', contents:''});
        },
        updateTip() {
            //
        },
        publish() {
            // save recipe into db
        }
    },
}
</script>

<style>
.mainpicture {
  width: 160px;
  height: 120px;
  border : solid 1px;
  text-align:center;
  cursor: pointer;
}

.pickpicture {
    display : none;
    z-index:5;
}

.crossed {
     background: 
         linear-gradient(to top left,
             rgba(0,0,0,0) 0%,
             rgba(0,0,0,0) calc(50% - 0.8px),
             rgba(0,0,0,1) 50%,
             rgba(0,0,0,0) calc(50% + 0.8px),
             rgba(0,0,0,0) 100%),
         linear-gradient(to top right,
             rgba(0,0,0,0) 0%,
             rgba(0,0,0,0) calc(50% - 0.8px),
             rgba(0,0,0,1) 50%,
             rgba(0,0,0,0) calc(50% + 0.8px),
             rgba(0,0,0,0) 100%);
}

.cookingOrderLabel {
  width: 100%;
  height: 100px;
  border : solid 1px;
  text-align:center;
  cursor: pointer;
}
</style>
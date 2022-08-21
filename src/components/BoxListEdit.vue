<template>
    <div class="main">
        <!-- {{boxList}} -->
        <div class="recipebox">
            <div class="wrap_edit" v-for="(box, $index) in boxList" :key="$index">
                <!-- <div class="alltitle hidden"></div> -->
                <div class="wrap_in" v-if="box.name">            
                    <form @submit="onSubmitForm">
                        <div class="wrap_text margin-top-5 margin-bottom-5">
                            <div class="title" v-if="isEdit === true && isSelectedBox === box.id">
                                <input class="width70" type="text" v-model="newName" maxlength="10" placeholder="박스 명(최대 13자)">
                                <label id="result-label" hidden for="result"></label><br/>
                            </div>
                            <div class="title" v-else>{{ box.name }} ({{ box.recipe?box.recipe.length:0}})</div>
                        </div>
                        <div class="wrap_buttonmargin-top-5 margin-bottom-5 margin-right-5">
                            <div class="btn btn-default margin-left-5 fr">
                                <span class="padding-right-5" @click="callEditBox(box.id)">수정</span><!--callEditBoxName(box.id); reload();-->
                            </div>
                            <div class="btn btn-default disabled margin-left-5 fr" v-if="box.isDefault==true">
                                <span class="padding-right-5">{{$t("button.delete")}}</span>
                            </div>
                            <div class="btn btn-default margin-left-5 fr" v-if="box.isDefault==false">
                                <span class="padding-right-5" @click="callDeleteBox(box.id)">{{$t("button.delete")}}</span>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { mapActions, mapGetters } from 'vuex';
export default {    
    name : "BoxListEdit",
    data: ()=>({
        isModalViewed : false,
        isEdit : false,
        isSelectedBox : 0,
        newName : ''
    }),
    computed: {
        ...mapGetters('box',['boxList'])
    },
    methods: {
        ...mapActions('box',['setBoxList']),
        async getRecipeBoxAll() {
            const response = await this.$api(`${this.$API_SERVER}/api/reciperecipebox/recipe/mine`, "get");
            if (response.status === this.HTTP_OK) {
                this.setBoxList(response.data)
            }
        },
        onSubmitForm(e) {
            console.log("onSubmitForm : "+e);
            let text = `"${this.newName}"로 레시피 박스 이름을 변경하시겠습니까?`;
            if (confirm(text) == true) {
                this.updateBoxName(this.newName);
                text = "변경 되었습니다.";
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
            this.isEdit = false
        },
        async updateBoxName(name) {
            console.log("updateBoxName : "+name);
            this.boxName = name;
            if(this.isSelectedBox > 0){
                const response = await this.$api(
                `${this.$API_SERVER}/api/recipebox/${this.isSelectedBox}`,
                "patch",
                {name: name}
                );
                if (response.status === this.HTTP_OK) {
                    this.selectedRecipeBox = response.data;
                }
            }
            this.getRecipeBoxAll()
        },
        async callEditBox(id) {
            console.log("callEditBox: ", id)
            if(this.isEdit === true){
                this.isEdit = false;
            } else{
                this.isEdit = true;
            }
            this.isSelectedBox= id;
        },
        async callDeleteBox(id) {
            console.log("callDeleteBox: ", id);
            const response = await this.$api(
            `${this.$API_SERVER}/api/recipebox/${id}`,
            "delete"
            );
            if (response.status === this.HTTP_OK) {
                console.log(response.data);
            }
            this.getRecipeBoxAll()
        },
    }
}
</script>
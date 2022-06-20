<template>
    <div>
        <div>
            <h4>{{ $t("title.recipeBox") }}</h4>
            <div class="menu">
                <ul>
                    <li>{{ $t("menu.myRecipe") }}</li>
                    <li>{{ $t("menu.savedRecipe") }}</li>
                    <li>{{ $t("menu.historyRecipe") }}</li>
                </ul>
            </div>
            <!--레시피박스 페이지에서 비활성화, 레시피 박스 선택시 활성화-->
            <div class="submenu" v-if="step===1">
                <ul>
                    <!-- DB 에서 가져온 레시피 박스 목록으로 생성
                    <li>{{ 레시피 박스 이름 }}</li>
                     -->
                    <li><button>{{$t("button.addNewBox")}}</button></li>
                </ul>
            </div>
            <div class="contents" v-if="step===0">
                <button>{{$t("button.edit")}}</button>
                <!-- DB로 부터 가져와야 레시피 박스 목록
                모든레시피는 4개의 레시피 이미지를 표시하고 
                나머지 레시피 박스는 1개의 레시피 대표 이미지로 표시
                
                목록의 처음부터 4개의 이미지 사용
                만일, 레시피 이미지가 없는 경우 디폴트 처리

                {{ 레시피 박스에 담긴 레시피 대표 이미지 }}
                {{ 레시피 박스 이름 }}
                {{ 박스에 담긴 레시피 수 }}
                -->
                <!-- DB에 레시피 박스 추가
                {{ 레시피 박스 추가 }}
                -->
            </div>
            <div class="contents" v-if="step===1">
                <button>{{$t("button.edit")}}</button>
                <!-- DB로 부터 가져와야 레시피 목록
                최대 5개의 레시피 목록을 가져온다.
                {{$t("content.all")}} {{ 박스에 담긴 레시피 수 }}
                
                {{ 레시피 대표 이미지 1 }}
                {{ 레시피 제목 }}
                {{ 레시피 부제목 }}
                {{ 레시피 단계 }}
                {{ 레시피 폴더 위치 }}
                -->
                <!-- 레시피 클릭시 해당 레시피 상세화면으로 이동 -->
            </div>
            <div class="contents" v-if="step===2">
                <button>{{$t("button.cancel")}}</button>
                <button>{{$t("button.done")}}</button>
                <!-- DB로 부터 가져와야 레시피 목록
                최대 5개의 레시피 목록을 가져온다.
                {{$t("content.all")}} {{ 박스에 담긴 레시피 수 }}
                
                {{ 레시피 대표 이미지 1 }}
                {{ 레시피 제목 }}
                {{ 레시피 부제목 }}
                {{ 레시피 단계 }}
                {{ 레시피 폴더 위치 }}
                {{ checkbox }}

                <div class="moveBox" v-if="moveStep===0">
                    <button>{{$t("button.move")}}</button>
                    <button>{{$t("button.delete")}}</button>
                </div>
                <div class="moveBox" v-if="moveStep===1">
                    {{$t("content.moveBox")}}
                    <button>{{$t("button.addNewBox")}}</button>
                    {{ 레시피 박스 대표 이미지 }}
                    {{ 레시피 박스 이름 }}
                    <button>{{$t("button.cancel")}}</button>
                </div>
                -->
            </div>
        </div>
    </div>
</template>
<script>

export default {
    name : "RecipeBoxView",
    data: ()=>({
        name : "",
        recipes : 0,
        selectedRecipeBox : [],
        /* 
            step=0 레시피 박스 화면
            step=1 레시시 박스 상세 화면
            step=2 레시피 박스 편집 화면
        */
        step : 0,
        /* 
            moveStep=0 레시피 박스 편집 화면
            moveStep=1 레시시 박스 편집 > 이동 화면
        */
        moveStep : 0
    }),

    methods : {

    },
}
</script>
<style>

</style>

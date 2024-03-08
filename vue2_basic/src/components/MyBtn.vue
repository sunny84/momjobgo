<template>
<!-- 컴포넌트 기초 -->
    <div
        :class="{ large }"
        :style="{ backgroundColor: color }" 
        class="btn">
        <slot></slot>
    </div><!--최상위 요소 1개-->
    <!-- 최상위 요소 2개 vue3에서 되나? vue2에서는 에러 발생-->
    <!-- <h1 v-bind="$attrs"></h1> -->
</template>
<script>
export default {
    inheritAttrs: false, // 속성 상속 여부
    created() { // lifeCycle 추가
        console.log(this.$attrs)
    },
    props: { // 부모 - 자식간의 데이터 통신
        color: {
            type: String,
            default: 'gray'
        },
        large: {
            type: Boolean,
            default: false
        }
    }
}
</script>
<style scoped>
    .btn {
        display: inline-block;
        margin: 4px;
        padding: 6px 12px;
        border-radius: 4px;
        background-color: gray;
        color: white;
        cursor: pointer;
    }
    .btn.large {
        font-size: 20px;
        padding: 10px 20px;
    }
</style>
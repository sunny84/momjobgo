<template>
    <div class="modal">
        <div class="overlay" @click="$emit('close-modal')"></div>
        <div class="modal-card">
            <slot/>
        </div>
    </div>
</template>
<script>
export default {};
</script>
<style>
/** Modal 
** modal: 컴포넌트를 감싸고 있는 부모
** overlay: 컴포넌트 영역과 같은 크기를 가진 배경(모달이 열리면 내용 말고 뒤에 있는 투명한 검정색 배경)
** modal-card: 내용이 보여질 영역
**/
.modal,
.overlay{
    width: 100%;
    height: 100%;
    position: fixed;
    left: 0;
    top: 0;
}
.overlay{
    opacity: 0.5;
    background-color: black;
}
.modal-card{
    position: relative;
    max-width: 80%;
    margin: auto;
    margin-top: 30px;
    padding: 20px;
    background-color: white;
    min-height: 500px;
    z-index: 10;
    opacity: 1;
}
</style>
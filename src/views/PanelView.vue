<template>
    <!-- <div id="panel">현재 카운터의 값: {{ count }}</div> -->
    <div id="panel">
        <p>현재 카운터의 값: {{ formatCnt }}</p>
        <p>현재 사용자의 이름: {{ name }}</p>
        <p>현재 사용자의 이름: {{ nameList }}</p>
        <!-- <p>현재 사용자의 이름: {{ $store.state.auth.name }}</p> // 지양한다.-->
    </div>


</template>
<script>
import { mapState, mapGetters } from 'vuex'
export default {
    computed: {
        // ...mapState(['count'])
        // count() {
        //     return this.$store.state.count
        // }
        ...mapGetters(['formatCnt']),
        ...mapGetters('auth', ['name', 'nameList'])
        // formatCnt(){
        //     return this.$store.getters.formatCnt
        // }
    }
}
</script>
<style scoped>
#panel {
    margin-bottom: 20px;
}
</style>
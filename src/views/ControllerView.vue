<template>
    <div>
        <!-- <button @click="increase">증가</button>
        <button @click="decrease" style="margin-left:10px;">감소</button> -->
        <button @click="changeCnt(1)">증가</button>
        <button @click="changeCnt(-1)" style="margin-left:10px;">감소</button>
    </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
    methods:{
        ...mapActions(['changeCnt'])
        // ...mapMutations(['changeCnt'])
        // increase() {
        // //     // this.$store.state.count++;
        // //     this.$store.commit('changeCnt', 1);
        //     this.$store.dispatch('changeCnt',2);
        // },
        // decrease() {
        // //     // this.$store.state.count--;
        // //     this.$store.commit('changeCnt', -1);
        //     this.$store.dispatch('changeCnt',-2);
        // }
    }
}
</script>

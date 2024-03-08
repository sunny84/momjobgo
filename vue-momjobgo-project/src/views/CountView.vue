<template>
  <div class="count">
    <h1>This is a count page</h1>
    <p>현재 카운터의 값 : {{formatCnt}}</p>
    <button @click="increase(1)">증가</button>
    <button @click="decrease(-1)">감소</button>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from "vuex"
export default {
  //TODO: 이해 안됨.
  data: () => ({
    count: 0
  }),
  methods : {
    formatCnt() {
      this.count = this.$store.dispath('count/formatCnt')
      console.log(this.count)
    },
    increase() {
      this.$store.dispatch("count/increment",1)
      // this.$store.state.count += 1;

    },
    decrease() {
      this.$store.dispatch("count/increment",-1)
      // this.$store.state.count -= 1;
    }
  },
  computed : { // 데이터 최적화, ReadOnly(읽기 전용) 처음만 연산하고 이후에는 캐싱된 값으로 출력
    //Getter
    ...mapGetters(['formatCnt']),
    // ...mapMutations(['increment']),
    // ...mapActions(['changeCnt']),
  }
}
</script>

<style scoped>
h1 {
    border-bottom: 1px solid #999;
}
p {
    color:blueviolet;
    margin-top: 20px;
}
button {
    color: #fff;
    background-color: blue;
    border-radius: 5px;    
    margin: 0;
    margin-left: 10px;
    padding: 5px 15px;
}
</style>
<template>
 <div>
  <!-- <Fruits /> -->
  <!-- <h1>{{ reverseMessage() }}</h1> -->
  <!-- <button @click="add">Add</button> -->
  <!-- <h1 
    :class="{ active: isActive }"
    @click="activate">
    {{ msg }}{{ isActive }}
  </h1> -->
  <!-- <ActiveStyle/> -->
  <!-- <MyBtn>Apple</MyBtn>
  <MyBtn large color="green"><span style="color:yellow">Banana</span></MyBtn>   -->
  <!-- <h1>{{ reversedMesage }}</h1> -->
  <!-- <CheckBox/> -->
  <!-- <SpliteImage3/> -->
  <!-- <MyConfirmDialogue/> -->
  <!-- <DynamicClass/> -->
  <!-- <DisplayMore/> -->

  <!-- ** VUEX 테스트 ** -->
  <!-- <Counter :count="count"></Counter> Not work-->
  <!-- <ControllerView></ControllerView>
  <PanelViewVue></PanelViewVue>
  <AuthView></AuthView> -->

  <!-- ** MODAL 테스트 -->
  <h1>모달 컴포넌트 만들기</h1>
  <ModalView v-if="isModalViewed" @close-modal="isModalViewed=false">
    <Content/>
  </ModalView>
  <button @click="isModalViewed=true">Open Modal</button>

 </div>
</template>

<script>
import Fruits from './components/Fruits.vue'
import MyBtn from './components/MyBtn.vue'
import CheckBox from './components/CheckBox.vue'
import SpliteImage from './components/SpliteImage.vue'
import DynamicClass from './components/DynamicClass.vue'
import SpliteImage3 from './components/SpliteImage3.vue'
import MyConfirmDialogue from './components/MyConfirmDialogue.vue'
import ActiveStyle from './components/ActiveStyle.vue'
import DisplayMore from './components/DisplayMore.vue'
import Counter from './components/Counter.vue'
import ControllerView from './views/ControllerView.vue'
import PanelViewVue from './views/PanelView.vue'
import AuthView from './views/AuthView.vue'
import Content from './components/Content.vue'
import ModalView from './components/ModalView.vue'


export default {
  data() {
    return {
      // Getter, Setter
      // msg: 'Hello Computed!',
      // isActive: false
      count: 0,
      isModalViewed:false,
    }
  },
  computed: {
    // // Getter
    // resersedMesage() {  // 캐싱된 데이터값
    //   return this.msg.split('').reverse().join('')
    // }
    // reversedMesage: {
    //   get() {
    //     return this.msg.split('').reverse().join('')
    //   },
    //   set(value) {
    //     console.log(value)
    //     this.msg = value
    //   }
    // }
  },
  watch: { // 데이터 감시 후 로직의 처리 가능
    // msg(newValue) {
    //   console.log('msg:', newValue)
    // },
    // reversedMesage(newValue) {
    //   console.log('reversedMessage:', newValue)
    // }
  },
  methods: {
    // reverseMessage() {
    //   return this.msg.split('').reverse().join('')
    // },
    // add() {
    //   this.reversedMesage += '!?'
    //   // this.msg += '!?'
    // },
    // changeMessage() {
    //   this.msg = 'Good!'
    // },
    // activate() {
    //   this.isActive = !this.isActive
    // },
  },
  components: {
    Fruits,
    MyBtn,
    CheckBox,
    SpliteImage,
    DynamicClass,
    SpliteImage3,
    MyConfirmDialogue,
    ActiveStyle,
    DisplayMore,
    Counter,
    ControllerView,
    PanelViewVue,
    AuthView,
    Content,
    ModalView
}
}
</script>
<style scoped>
  /* .active {
    color: red;
    font-weight: bold;
  } */
</style>
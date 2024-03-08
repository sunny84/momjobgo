<template>
    <div>
        <h1 
            :class="{ active: isActive }"
            @click="activate">
            {{ msg }}{{ isActive }}
        </h1>
    </div>
</template>
<script>
export default {
  data() {
    return {
      // Getter, Setter
      msg: 'Hello Computed!',
      isActive: false
    }
  },
  computed: {
    // // Getter
    // resersedMesage() {  // 캐싱된 데이터값
    //   return this.msg.split('').reverse().join('')
    // }
    reversedMesage: {
      get() {
        return this.msg.split('').reverse().join('')
      },
      set(value) {
        console.log(value)
        this.msg = value
      }
    }
  },
  watch: { // 데이터 감시 후 로직의 처리 가능
    msg(newValue) {
      console.log('msg:', newValue)
    },
    reversedMesage(newValue) {
      console.log('reversedMessage:', newValue)
    }
  },
  methods: {
    reverseMessage() {
      return this.msg.split('').reverse().join('')
    },
    add() {
      this.reversedMesage += '!?'
      // this.msg += '!?'
    },
    changeMessage() {
      this.msg = 'Good!'
    },
    activate() {
      this.isActive = !this.isActive
    },
  },
}
</script>
<style scoped>
  .active {
    color: red;
    font-weight: bold;
  }
</style>
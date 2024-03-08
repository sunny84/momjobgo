<template>
  <div class="calculate">
    <h1>This is Arithmetic Operations page.</h1>   
    <div class="wrap">
      <input type="number" v-model="num1" @input="$store.dispatch('calculate/setNum1', $event.target.value)"/>
      <label>{{calc}}</label>
      <input type="number" v-model="num2" @input="$store.dispatch('calculate/setNum2', $event.target.value)"/>
      <label>=</label>
      <input type="number" v-model="ret" readonly/>
    </div>
    <button @click="addition">더하기</button>
    <button @click="subtraction">빼기</button>
    <button @click="multiplication">곱하기</button>
    <button @click="division">나누기</button>
  </div>
</template>

<script>
export default {
  data: () => ({
    num1: 0,
    num2: 0,
    ret: 0,
    calc: "{산술연산자}"
  }),

  methods: {
    calculate() {
      this.$store.dispath('calculate/test', {
        num1: this.num1,
        num2: this.num2,
        calc: this.calc
      })
    },
    addition() {
      this.calc = "+"
      this.ret = parseFloat(this.num1) + parseFloat(this.num2);
    },
    subtraction() {
      this.calc = "-"
      this.ret = parseFloat(this.num1) - parseFloat(this.num2);
    },
    multiplication() {
      this.calc = "*"
      this.ret = parseFloat(this.num1) * parseFloat(this.num2);
    },
    division() {
      this.calc = "/"
      this.ret = parseFloat(this.num1) / parseFloat(this.num2);
    }
  },

  computed: {
    // ...mapGetters(['addition', 'subtraction', 'multiplication', 'division']),
    // ...mapGetters(['calculate'])
    // addition : function() {
    //   this.calculate = "+"
    //   this.ret = parseFloat(this.num1) + parseFloat(this.num2);
    // },
    // subtraction() {
    //   this.calculate = "-"
    //   this.ret = parseFloat(this.num1) - parseFloat(this.num2);
    // },
    // multiplication() {
    //   this.calculate = "*"
    //   this.ret = parseFloat(this.num1) * parseFloat(this.num2);
    // },
    // division() {
    //   this.calculate = "/"
    //   this.ret = parseFloat(this.num1) / parseFloat(this.num2);
    // }
  },
  watch: {
    result(value) {
      console.log('msg:', this.ret, value)
    }
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
.wrap {
  display: flex;
}
input {
  width: 100px;
  height: 20px;
  overflow: hidden;
  /* border: 1px solid #333; */
  background-color: skyblue;
  margin: 10px 10px;
  text-align: center;
}
label {
  margin-top: 10px;
}
input:read-only {
  background-color: #ccc;
}
button {
    width: 80px;
    color: #fff;
    background-color: midnightblue;
    border-radius: 5px;    
    margin: 0;
    margin-left: 10px;
    padding: 5px 15px;
}
</style>
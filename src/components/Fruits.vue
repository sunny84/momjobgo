<template>
<!-- 템플릿 문법/Computed/Getter, Setter/Watch -->
  <section>
    <div>
      <h1>Fruits</h1>
      <ul>
        <li
          v-for="fruit in fruits"
          :key="fruit">
          {{ fruit }}
        </li>
      </ul>
    </div>
    <div>
      <h1>Reverse Fruits</h1>
      <ul>
        <li
          v-for="fruit in reverseFruits"
          :key="fruit">
          {{ fruit }}
        </li>
      </ul>
    </div>
  </section>
</template>
<script>
export default{
  data() {
    return {
      fruits: [
        'Apple', 'Banana', 'Cherry'
      ]
    }    
  },
  computed: {
    hasFruit() {
      return this.fruits.length > 0
    },
    reverseFruits() {
      return this.fruits.map(fruit => {
        // 'Apple' => ['A','p','p','l','e']
        // => ['e', 'l', 'p', 'p', 'A'] => 'elppA'
        return fruit.split('').reverse().join('')
      })
    }
  }
}
</script>
<style scoped>
ul{
  list-style:none;
  padding-left:0px;
}
</style>

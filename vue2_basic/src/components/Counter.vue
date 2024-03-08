<template>
    <div id="counter">
        <h1>Counter</h1>
        <span>{{ count }}</span>
        <button @click="increment">+</button>
        <button @click="decrement">-</button>
        <button @click="incrementIfOdd">Increment if odd</button>
        <button @click="incrementAsync">Increment async</button>
        <div>
            <div>Recent History (last 5 entries): {{ history }}</div>
        </div>
    </div>
</template>
<script>
import { mapState, mapActions, mapGetters } from "vuex";
export default {
    // props: ['count']
    computed: {
        ...mapState(['count']),
        ...mapGetters(['count','history'])
    },
    methods: mapActions([
      'increment',
      'decrement',
      'incrementIfOdd',
      'incrementAsync'])
}
</script>
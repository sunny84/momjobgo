<template>
    <div style="margin-top: 20px;">
        <input type="text" placeholder="이름을 입력하세요." @input="$store.dispatch('auth/setName', $event.target.value)">
        <button @click="$store.dispatch('auth/setNameList', names)">update</button>
        <button @click="update(names)">update2</button>
        <br/>
        <br/>        
        {{ names }}
    </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
    data: () => ({
        names: [
            {name: 'sun',
            height: 150},
            {name: 'woo',
            height: 170},
            {name: 'min',
            height: 180},
            {name: 'you',
            height: 160}]
    }),

    created() {
        this.initialize()
    },
    computed: {

    },
    methods: {
        ...mapActions('auth', ['setNameList']),
        
        initialize() {
            this.setNameList(this.names)
        },
        update(names){
            this.setNameList(names)
        }
    }
}
</script>
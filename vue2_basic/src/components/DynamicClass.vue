<template>
    <section>
        <div 
            v-for="item in items"
            :key="item"
            v-bind:class="[ item.purchased? 'done' : 'important' ]"
        >
        <!-- 
            v-bind:class="{ done: item.purchased }" 

            v-bind:class="{ !done: item.purchased }" 
            v-bind:class="{ done: item.purchased === false }"

            v-bind:class="[ item.purchased? 'done' : '' ]"
            v-bind:class="[ item.purchased? 'done' : 'important' ]"
            v-bind:class="[ item.purchased? 'done' : '', item.status? 'ready' : '' ]"
            v-bind:class="[ item.purchased? 'done' : '', 'ready' ]"

            v-bind:class="[ item.purchased? 'done' : '', 'ready' ]"

            class="ready"
            v-bind:class="[ item.purchased? 'done' : '']"

            * shortcut 문법
            :calss="[ item.purchased? 'done' : '' ]"

            *토글 기능 추가
            @click="toggleFn(item)"

            methods: {
                toggleFn(item) {
                    item.purchased = !item.purchased
                }
            }
        -->
            {{ item.subject }}
        </div>
    </section>
</template>
<script>
export default {
    data() {
        return {
            items: [{
                subject: '10 pens',
                purchased: false,
                status: true,
            },
            {
                subject: '20 bags',
                purchased: true,
                status: false,
            },
            {
                subject: '5 coffee',
                purchased: false,
                status: true,
            },
            {
                subject: '8 towels',
                purchased: true,
                status: false,
            },
            ]
        }
        
    },
}
</script>
<style scoped>
body {
    cursor: pointer;
    user-select: none; /* 선택시 block 방지 */
}
.done {
    text-decoration: line-through;
    color: #828282;
}
.important {
    font-weight: bold;
}
.ready {
    font-weight: bold;
    color: red;
}

</style>
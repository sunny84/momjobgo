<template>
  <v-app-bar app v-if="visible.header">
    <v-app-bar-title>{{title}}</v-app-bar-title>
  </v-app-bar>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  computed : {
    ...mapGetters('page',['title', 'visible'])
  }
};
</script>

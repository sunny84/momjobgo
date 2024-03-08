<template>
  <v-footer app v-if="visible.footer">
    <v-card flat tile class="text-center">
      <v-card-text class="black--text">"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."</v-card-text>
    </v-card>
  </v-footer>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  computed : {
    ...mapGetters('page',['visible'])
  }
};
</script>

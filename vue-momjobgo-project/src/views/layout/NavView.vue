<template>
  <v-navigation-drawer app v-if="visible.footer">
    <v-list-item>
      <v-list-item-content>
        <v-list-item-title class="text-h6">Vue-Project</v-list-item-title>
        <v-list-item-subtitle> 님 환영합니다. </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-divider></v-divider>

    <v-list dense nav>
      <v-list-item v-for="(item, key) in menuList" :key="key" :to="item.path">
        <v-list-item-icon>
          <v-icon>{{ item.meta.icon }}</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>{{ item.name }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <!-- <v-list-item @click="logout">
        <v-list-item-icon>
          <v-icon>logout</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>logout</v-list-item-title>
        </v-list-item-content>
      </v-list-item> -->
    </v-list>
  </v-navigation-drawer>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  data: () => ({
    
  }),

  computed : {  
    ...mapGetters('page', ['menuList', 'visible'])
  },

  methods : {
    logout(){
      alert('미구현입니다.');
    }
  }

};
</script>

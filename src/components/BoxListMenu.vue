<template>
    <div>   
        <h1 class="fl"><div class="button btn2 btn-link" @click="$router.go(-1)"><img src="@/assets/images/icon_back.png" alt="돌아가기" title="돌아가기"/></div>
        <span class="color-orange padding-left-15">{{ $t("title.recipeBox") }}</span>
        </h1>
        <div class="wrap_menu">
            <ul>
                <li class="menu" :class="{on : this.$route.path == '/myrecipe'}" style="cursor: ponter;" @click="rediectPage(1)">{{ $t("menu.myRecipe") }}</li>
                <li class="menu" :class="{on : this.$route.path.includes('/recipebox')}" style="cursor: ponter;" @click="rediectPage(2)">{{ $t("menu.savedRecipe") }}</li>
                <li class="menu" :class="{on : this.$route.path == '/todaySawRecipe'}" style="cursor: ponter;" @click="rediectPage(3)">{{ $t("menu.historyRecipe") }}</li>
            </ul>
        </div>
    </div>
</template>
<script>
import { mapGetters } from "vuex";
export default {
    name : "BoxListMenu",
    data: ()=>({
    }),

    computed : {  
        ...mapGetters('box', ['recipeId'])
    },

    methods:{        
      rediectPage(flag) {
        if(flag == 1) {
          if(this.$route.path.includes('/myrecipe')===false)
            this.$router.push('/myrecipe')
        } else if(flag == 2) {
          if(this.$route.path.includes('/recipebox')===false)
            this.$router.push('/recipeboxlist/' + this.recipeId)
        } else if(flag == 3) {
          if(this.$route.path.includes('/todaySawRecipe')===false){
            console.log('not implemented!')
            // this.$router.push('/todaySawRecipe/')
          }
        }
        console.log(flag, this.$route.path)
      }
    }
}
</script>

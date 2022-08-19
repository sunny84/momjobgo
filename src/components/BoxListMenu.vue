<template>
    <div>   
        <h1 class="fl"><router-link :to="'/recipedetail/'+recipeId"><img src="@/assets/images/icon_back.png" alt="돌아가기" title="돌아가기"/></router-link>
        <span class="color-orange padding-left-15">{{ $t("title.recipeBox") }}</span>
        </h1>
        <div class="wrap_menu">
            <ul>
                <li class="menu" :class="{on : this.$route.path == '/myrecipe'}" style="cursor: ponter;" @click="rediectPage(1)">{{ $t("menu.myRecipe") }}</li>
                <li class="menu" :class="{on : this.$route.path == '/recipeboxlist/'+recipeId}" style="cursor: ponter;" @click="rediectPage(2)">{{ $t("menu.savedRecipe") }}</li>
                <li class="menu" :class="{on : this.$route.path == '/todaySawRecipe'}" style="cursor: ponter;" @click="rediectPage(3)">{{ $t("menu.historyRecipe") }}</li>
            </ul>
        </div>
    </div>
</template>
<script>
export default {
    name : "MyRecipeView",
    data: ()=>({
      recipeId : 47,
    }),
    
    methods:{        
      rediectPage(flag) {
        console.log(flag)
        if(flag == 1)
          this.$router.push('/myrecipe')
        else if(flag == 2)
          this.$router.push('/recipeboxlist/' + this.recipeId)
        else if(flag == 3)
          this.$router.push('/todaySawRecipe')
      }
    }
}
</script>

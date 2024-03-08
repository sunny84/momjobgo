import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import RecipeWriteView from "@/views/RecipeWriteView.vue";
import LoginView from "@/views/LoginView.vue";
import RecipeBoxView from "@/views/RecipeBoxView.vue";
import MyRecipeView from "@/views/MyRecipeView.vue";
import RecipeFilterView from "@/views/RecipeFilterView.vue";
import RecipeListView from "@/views/RecipeListView.vue";
import RecipeBoxListView from "@/views/RecipeBoxListView.vue";
import RecipeDetailView from "@/views/RecipeDetailView.vue";
import TodaySawRecipeView from "@/views/TodaySawRecipeView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "recipelist",
    component: RecipeListView,
  },
  {
    path: "/recipeboxlist/:recipeId",
    name: "recipeboxlist",
    component: RecipeBoxListView,
  },
  {
    path: "/recipebox/:boxId",
    name: "recipebox",
    component: RecipeBoxView,
  },
  {
    path: "/myrecipe",
    name: "myrecipe",
    component: MyRecipeView,
  },
  {
    path: "/todaySawRecipe",
    name: "todaySawRecipe",
    component: TodaySawRecipeView,
  },
  {
    path: "/write",
    name: "write",
    component: RecipeWriteView,
  },
  {
    path: "/recipedetail/:recipeId",
    name: "recipedetail",
    component: RecipeDetailView,
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
  },
  {
    path: "/login/:nextUrl",
    name: "login",
    component: LoginView,
  },
  {
    path: "/recipelist",
    name: "recipelist",
    component: RecipeListView,
  },
  {
    path: "/filter",
    name: "filter",
    component: RecipeFilterView,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;

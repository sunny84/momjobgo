import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import common from "./mixins/common";
import call from "./mixins/call";
import i18n from "./i18n";

Vue.config.productionTip = false;

Vue.mixin(common);
Vue.mixin(call);

new Vue({
  router,
  store,
  i18n,
  render: (h) => h(App),
}).$mount("#app");

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import stripe from './plugins/stripe'
import "material-design-icons-iconfont/dist/material-design-icons.css"

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  stripe,
  render: h => h(App)
}).$mount('#app')

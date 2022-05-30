import App from './App'

// #ifndef VUE3
import Vue from 'vue'
import {blogsRequest} from './network/api.js'
import store from './store'
import uView from '@/uni_modules/uview-ui'

Vue.prototype.$store = store
// Vue.prototype.$blogsRequest = blogsRequest
Vue.prototype.$blogsRequest = blogsRequest
Vue.config.productionTip = false
Vue.use(uView)
App.mpType = 'app'
const app = new Vue({
    ...App,
	store
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif
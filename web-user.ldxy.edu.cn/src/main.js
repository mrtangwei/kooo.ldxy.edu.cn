
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'

import VueRouter from 'vue-router'
//import VueResource from 'vue-resource'
import store from './vuex/store'
import Vuex from 'vuex'

import routes from './routes'
//import Mock from './mock'
import myUpload from 'vue-image-crop-upload'




//Mock.bootstrap();
import 'font-awesome/css/font-awesome.min.css'
Vue.use(ElementUI)
Vue.use(VueRouter)
//Vue.use(VueResource)
Vue.use(Vuex)
Vue.use(myUpload)



//NProgress.configure({ showSpinner: false });

const router = new VueRouter({

  routes
})


router.beforeEach((to, from, next) => {

  if (to.path == '/login') {
    sessionStorage.removeItem('user');
  }
  let user = JSON.parse(sessionStorage.getItem('user'));
  if (!user && to.path != '/login') {
    next({ path: '/login' })
  } else {
    next()
  }
})

//自定义过滤器 小数点取后两位
Vue.filter('doubleFilter', function (value, row, column) {
    var toFixedNum = Number(value).toFixed(3);
    var realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1);
    return realVal;
})

new Vue({
  router,
  store,
  //components: { App }
  render: h => h(App)
}).$mount('#app')

import axios from 'axios'
axios.defaults.withCredentials=true
Vue.prototype.$http = axios;

//Vue.http.xhr = { withCredentials: true };
/* import global_ from './components/Global'
Vue.use(global_);*/


const serverList = function() {


    this.$http.get('/api/security/server/all').then(response => {
        this.serverList = response.data;

        return  this.serverList;

    }, response => {
        if(response.status == 401){
            console.log("请登录！");
            this.$router.push('/login');
        } else if(response.status == 403){
            console.log("权限不够，请联系管理员！");
            this.$router.push('/login');
        }

    })
}
Vue.prototype.ServerList = serverList;
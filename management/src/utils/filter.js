import Vue from 'vue'

//全局过滤器
Vue.filter("DateFormat",value =>{
    return value &&  new Date(value).toLocaleDateString().replaceAll("/","-")
})
import axios from 'axios';
import {Message} from 'element-ui'
import router from '@/router'

const instance = axios.create({
    baseURL:"http://localhost:666",
    timeout:10000
})
//请求拦截器
instance.interceptors.request.use(config =>{
    
    const token = localStorage.getItem("m-authorization-token");
    console.log(token);
    if(token && !config.url.endsWith("/login") && !config.url.endsWith("/captchaImage")){
        config.headers["Authorization"] =  token;
    }
    
    return config;
},err =>{
    return Promise.reject(err)
})
//响应拦截器
instance.interceptors.response.use(res =>{
    let res_data = res.data;
    if(res_data.code && res_data.code != 200){
        Message.error(res_data.message || '网络请求错误');

        if(res_data.code == 401){
            //401一般表示token过期或没有带
            localStorage.removeItem("m-authorization-token");
            router.push("/login")
        }


        return false;
    }
    return res_data;
},err =>{
    return Promise.reject(err)
})

export default instance
import { GetUserInfoAPI } from "@/request/api"
export default{
    namespaced:true,
    state:{
        //用户信息
        userInfo:JSON.parse(localStorage.getItem("m-userInfo")) || {
            
        }
    },
    mutations:{
        changeUserInfo(state,payload){
            state.userInfo = payload
            localStorage.setItem("m-userInfo",JSON.stringify(state.userInfo))
        }
    },
    actions:{
        async asyncChangeUserInfo({commit},payload){
            let userInfoRes = await GetUserInfoAPI()
            console.log(userInfoRes);
            if(!userInfoRes) return;
            commit('changeUserInfo',userInfoRes.data)
        }
    }
}
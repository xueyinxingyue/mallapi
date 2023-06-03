export default{
    namespaced:true,
    state:{
        isCollapse:false
    },
    mutations:{
        changeIsCollapse(state){
            //取反
            state.isCollapse = !state.isCollapse
        }
    }
}
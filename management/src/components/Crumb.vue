<template>
    <div class="crumb">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item v-for="item,index in titles" :key="index">{{item}}</el-breadcrumb-item>
            <!-- <el-breadcrumb-item>活动详情</el-breadcrumb-item> -->
        </el-breadcrumb>
    </div>
</template>

<script>

export default {
    data () {
        return {
            //titles的值和当前所在的路由相关
            //当前路由一发生变化，titles的值就变化,所以要监听路由的变化
            titles:[]
 
        }
    },
    watch:{
        $route:{
            immediate: true, //刷新页面立即监听
            handler(val,oldVal){
                //当前路由：this.$route  里面可以有元信息(meta存储路由相关信息)
                this.titles = this.$route.meta.titles
            }
        }
    }
}
</script>
 
<style lang = "less" scoped>
    .crumb .el-breadcrumb{
        line-height: 50px;
        font-size: 15px;
    }
</style>
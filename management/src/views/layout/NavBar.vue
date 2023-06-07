<template>
    <div class="navbar" :class="{isColl:isCollapse}">
        <!--
            @open="handleOpen" 
            @close="handleClose" 
            collapse="true"
            unique-opened = "true"  同一时间内，只有一个菜单展开
        -->
        <h1 class="main-logo">
            <img src="@/assets/images/logo.png" alt="" width="32">
            <span v-show="!isCollapse">后台管理系统</span>
        </h1>
        <el-menu 
            :default-active="$route.path" 
            class="el-menu-vertical-demo" 
            background-color="#304156" 
            text-color="#fff" 
            active-text-color="#ffd04b"
            :unique-opened = "true"     
            :collapse="isCollapse"
            :collapse-transition="false"
            :router="true"
            >
           <div v-for="item,idx in menuData" :key="idx">
            
            <el-menu-item :index="item.path">
                <i :class="item.meta.icon"></i>
                <!-- <svg-icon :icon-file-name="item.meta.icon" style="margin: 0 10px 0 4px"></svg-icon> -->
                <span slot="title">{{item.meta.title}}</span>
            </el-menu-item>
           </div>
            
        </el-menu>
    </div>
</template>

<script>
import {mapState} from 'vuex'
export default {
    data() {
        return {
            menuData:[
                {
                    path: '/home',
                    component: () => import('../HomeView.vue'),
                    meta:{
                        title:'首页',
                        icon:'el-icon-s-home'
                    }
                },
                {
                    path:"/category",
                    meta:{
                        title:"商品分类",
                        icon:"el-icon-menu"
                    }
                
                },
                {
                    path:"/product",
                    meta:{
                        title:"所有商品",
                        icon:"el-icon-goods"
                    }
                },
                {
                    path:"/user",
                    meta:{
                        title:"用户管理",
                        icon:"el-icon-user"
                    }
                },
                {
                    path:"/order",
                    meta:{
                        title:"所有订单",
                        icon:"el-icon-s-order"
                    }
                },
            ]
        }
    },
    computed:{
        ...mapState({
            isCollapse:state => state.navCollapse.isCollapse,
            // menuData:state => state.userMenuData.menuData
        })
    }
}
</script>
 
<style lang = "less" scoped>
.navbar {
    width: 200px;
    height: 100%;
    position: fixed;
    background-color: #304156;
    box-shadow: 5px 0px 5px #ccc;
    transition: all .3s;
    .el-menu {
        border: none;
    }
    &.isColl{
        width: 64px;
    }
    img{
        vertical-align: middle;
    }
    .main-logo{
        padding: 10px 16px;
        position: relative;
        span{
            min-width: 130px;
            color:#fff;
            font-weight: bold;
            display: inline-block;
            position: absolute;
            left: 62px;
            top: 16px;
        }
    }
    /* 解决加了div之后的样式显示效果 */
    .el-menu--collapse .el-submenu__title span,::v-deep .el-menu--collapse .el-submenu__title .el-submenu__icon-arrow{
        display: none;
    }
    /*处理子菜单的背景和鼠标移上的背景 */
    .el-submenu .el-menu-item{
        background-color: rgb(38,52,69)!important;
    }

    .el-menu .el-menu-item:hover,::v-deep .el-submenu__title:hover{
        background-color: #444!important;
    }
}
</style>
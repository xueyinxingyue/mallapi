<template>
    <div class="layout">
        <NavBar></NavBar>
        <div class="layout-right" :class="isCollapse ? 'isColl1':'isColl2'">
            <HanderView></HanderView>
            <ContentView></ContentView>
        </div>
    </div>
</template>

<script>
import NavBar from './NavBar.vue'
import HanderView from './HanderView.vue'
import ContentView from './ContentView.vue'
import { mapState } from 'vuex'
export default {
    data () {
        return {
            

 
        }
    },
    components:{
        NavBar,
        HanderView,
        ContentView
    },
    computed:{
        ...mapState({
            isCollapse:state => state.navCollapse.isCollapse//,
            // menuData:state => state.userMenuData.menuData
        })
    }
}
</script>
 
<style lang = "less" scoped>
    .layout{
        width: 100%;
        height: 100%;
        display: flex;
        .isColl1{
            padding-left: 64px;
        }
        .layout-right{
            flex: 1;
            
        }
        .isColl2{
            padding-left: 200px;
        }
        
    }
</style>
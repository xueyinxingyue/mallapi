<template>
    <div class="tags">
        <el-tag class="tag" size="medium" :closable="index > 0"
            v-for="item,index in tags" 
            :key="item.path"
            :effect="item.title == $route.meta.titles[$route.meta.titles.length - 1]?'dark':'plain'"
            @click="goTo(item.path)"
            @close="close(index)"
            :disable-transitions="true"
        >
            <i class="cir" v-show="item.title == $route.meta.titles[$route.meta.titles.length - 1]"></i>
            {{ item.title }}
        </el-tag>
    </div>
</template>

<script>

export default {
    data () {
        return {
            tags:[
                {
                    title:"首页",
                    path:"/home"
                }
            ],
            
 
        }
    },
    methods:{
        goTo(path){
            this.$router.push(path)
        },
        close(index){
            //先跳转，在删除
            if(this.tags[index].path === this.$route.path && index !== this.tags.length - 1){
                this.$router.push(this.tags[this.tags.length - 1].path)
            }else if(index === this.tags.length - 1){
                this.$router.push(this.tags[this.tags.length - 2].path)
            }

            //关闭当前页，本质就是删除tags的对应项
            this.tags.splice(index,1)
            //跳转到最后一项
            
            
        }
    },
    watch:{
        $route:{
            immediate: true, //立即监听
            handler(val,oldVal){
                const bool = this.tags.find(item => {
                    console.log(val.path,"~~~~~~",item.path);
                    return val.path == item.path
                })
                if(!bool){
                    this.tags.push({
                    title:val.meta.titles[val.meta.titles.length - 1],
                    path:val.path
                })
                }
                
            }
        }
    }
}
</script>
 
<style lang = "less" scoped>
    .tags{
        padding-left: 20px;
        padding-top: 5px;
        .tag{
            cursor: pointer;
            margin-right: 5px;
            .cir{
                width: 8px;
                height: 8px;
                background-color: #fff;
                border-radius: 50%;
                display: inline-block;
                margin-right: 4px;
            }
        }
    }
</style>
<template>
    <div>
        <input type="text" :value="value" @input="hdInput($event.target.value)">
    </div>
</template>

<script>
export default {
    props:["value"],
    model:{
        prop:"value",
        event:"fn"   // 事件名
    },
    data () {
        return {

 
        }
    },
    methods:{
        hdInput(val){
            this.$emit("fn", val)
        }
    }
}
</script>
 
<style lang = "less" scoped>
    
</style>
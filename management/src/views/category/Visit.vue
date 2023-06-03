<template>
    <div>
        <myInput v-model="value"></myInput>
        <p>{{value}}</p>
    </div>
</template>

<script>
import myInput from "./myInput"
export default {
    components:{
        myInput
    },
    data () {
        return {

            value:"初始值"
        }
    }
}
</script>
 
<style lang = "less" scoped>
    
</style>
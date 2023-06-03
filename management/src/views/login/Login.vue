<template>
    <div class="login-page">
        <div class="login-box">
            <h1>后台管理系统</h1>
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="用户名" prop="username">
                <el-input type="text" v-model="ruleForm.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item :label="'密\xa0\xa0\xa0\xa0码'" prop="password">
                <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="captchacode">
                <div class="captcha-box">
                    <el-input v-model.number="ruleForm.captchacode"></el-input>
                    <img height="40" :src="captchaSrc" @click="getCaptchaCode"/>
                </div>
                
            </el-form-item>
            <el-form-item id="login-btn-box">
                <el-button class="login-btn" type="primary" @click="submitForm('ruleForm')">登录</el-button>
            </el-form-item>
        </el-form>
        </div>
    </div>
</template>

<script>
import {validateUsername} from '@/utils/validate.js';
import {GetCaptchaAPI,LoginAPI} from '@/request/api.js'
import {mapMutations,mapActions} from 'vuex'
export default {
    created(){
        //验证码请求
        this.getCaptchaCode()
        //清除vue中的userMenuData
        // this.changeMenuData([])
    },
    data () {
        return {
            //图片验证码路径
            captchaSrc:"",
            ruleForm:{
                username: "",
                password: "",
                captchacode: ""
            },
            rules:{
                username:[
                    {
                        required:true,              //必填项
                        message:"用户名不能为空",    //提示语
                        trigger:"blur"
                    },
                    //自定义校验写法
                    { validator: validateUsername, trigger: 'blur' }
                ],
                password:[
                    {
                        required:true,              //必填项
                        message:"密码不能为空",      //提示语
                        trigger:"blur"
                    }
                ],
                captchacode:[
                    {
                        required:true,              //必填项
                        message:"验证码不能为空",    //提示语
                        trigger:"blur"
                    }
                ]

            }
 
        }
    },
    methods:{
        ...mapActions({
            asyncChangeUserInfo: "userInfo/asyncChangeUserInfo"
        }),
        // ...mapMutations({
        //     changeMenuData: "userMenuData/changeMenuData"
        // }),
        async getCaptchaCode(){
            let res = await GetCaptchaAPI()
            if(!res){
                this.$message.error("验证码获取失败")
                return;
            }
            this.$message.success(res.message);
            //展示验证码图片
            this.captchaSrc = "data:image/gif;base64," + res.data.img
            //保存uuid，给到登录时作为参数传递到后端
            localStorage.setItem("m-captcha-uuid",res.data.uuid)
            
        },
        submitForm(formName){
            this.$refs[formName].validate(async (valid) => {
            if (valid) {
                //校验通过，执行该代码
                //发起登录请求
                let res = await LoginAPI({
                    username:this.ruleForm.username,
                    password:this.ruleForm.password,
                    code:this.ruleForm.captchacode,
                    uuid:localStorage.getItem("m-captcha-uuid")
                });
                if(!res){
                    return;
                }
                //提示用户登录成功
                this.$message.success(res.message)
                //清除uuid
                localStorage.removeItem("m-captcha-uuid")
                //保存token
                localStorage.setItem("m-authorization-token",res.data.token)
                //跳转首页
                this.$router.push("/")
                console.log(res);

                this.asyncChangeUserInfo()
                
            } else {
                //校验没有通过，执行这里代码
                console.log('error submit!!');
                this.$message({
                    message: '请输入正确信息',
                    type: 'warning'
                });
                return false;
            }
            });
        }
    }
}
</script>
 
<style lang = "less" scoped>
    .login-page{
        width: 100%;
        height: 100%;
        background: url('../../assets/images/loginBg.jpg') center top no-repeat;
        position: relative;
        .login-box{
            width: 400px;
            background-color: #fff;
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%,-50%);
            padding-top: 20px;
            padding-right: 40px;
            border-radius: 20px;
            h1{
                text-align: center;
                margin-bottom: 20px;
                padding-left: 40px;
                font-size: 20px;
            }
            .captcha-box{
                display: flex;
                img{
                    margin-left: 20px;
                }
            }

            .login-btn{
                width: 100%;
            }
            /* 样式覆盖写法  ::v-deep  */
            ::v-deep #login-btn-box .el-form-item__content{
                margin-left: 40px!important;
            }
        }
    }
</style>
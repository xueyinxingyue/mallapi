//用来存放项目中所有的验证方法
export function validateUsername(rule, value, callback){
    //需求: 用户名输入3-20为才可以校验通过
    if(value.length < 3 || value.length > 20){
        callback(new Error('用户名必须在3-20位的字符内！'));
    }else{
        callback();
    }
}
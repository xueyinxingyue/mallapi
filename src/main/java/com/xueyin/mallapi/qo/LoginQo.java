package com.xueyin.mallapi.qo;

/**
 * 封装登录条件类
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginQo {
    private String username;
    private String password;
    private String code;
    private String uuid;
}

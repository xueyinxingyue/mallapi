package com.xueyin.mallapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xueyin
 * @since 2023-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String userNickname;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 姓名
     */
    private String userRealname;

    /**
     * 性别
     */
    private Boolean userGender;

    /**
     * 出生日期
     */
    private LocalDate userBirthday;

    /**
     * 所在地地址
     */
    private String userAddress;

    /**
     * 家乡
     */
    private String userHomeplace;

    /**
     * 用户头像
     */
    private String userProfilePictureSrc;


}

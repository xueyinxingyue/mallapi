package com.xueyin.mallapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产品订单表
 * </p>
 *
 * @author xueyin
 * @since 2023-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Productorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "productorder_id", type = IdType.AUTO)
    private Integer productorderId;

    /**
     * 订单号
     */
    private String productorderCode;

    /**
     * 收货地址
     */
    private String productorderAddress;

    /**
     * 收货详细地址
     */
    private String productorderDetailAddress;

    /**
     * 邮政编码
     */
    private String productorderPost;

    /**
     * 收货人
     */
    private String productorderReceiver;

    /**
     * 联系方式
     */
    private String productorderMobile;

    /**
     * 支付日期
     */
    private Date productorderPayDate;

    /**
     * 发货日期
     */
    private Date productorderDeliveryDate;

    /**
     * 确认日期
     */
    private Date productorderConfirmDate;

    /**
     * 订单状态(0:待付款 1:待发货 2:待确认 3:交易成功 4:交易关闭)
     */
    private Integer productorderStatus;

    /**
     * 用户id
     */
    private Integer productorderUserId;


}

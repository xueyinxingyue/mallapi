package com.xueyin.mallapi.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产品订单详细表
 * </p>
 *
 * @author xueyin
 * @since 2023-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Productorderitem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "productorderitem_id", type = IdType.AUTO)
    private Integer productorderitemId;

    /**
     * 数量
     */
    private Integer productorderitemNumber;

    /**
     * 单价
     */
    private BigDecimal productorderitemPrice;

    /**
     * 关联产品id
     */
    private Integer productorderitemProductId;

    /**
     * 关联订单id
     */
    private Integer productorderitemOrderId;

    /**
     * 关联用户id
     */
    private Integer productorderitemUserId;

    /**
     * 用户备注
     */
    @TableField("productorderitem_userMessage")
    private String productorderitemUsermessage;

    //包含的商品的信息
    @TableField(exist = false)
    private Product product;

}

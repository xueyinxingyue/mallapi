package com.xueyin.mallapi.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产品表
 * </p>
 *
 * @author xueyin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品标题
     */
    private String productTitle;

    /**
     * 原价
     */
    private BigDecimal productPrice;

    /**
     * 促销价
     */
    private BigDecimal productSalePrice;

    /**
     * 创建日期
     */
    private Date productCreateDate;

    /**
     * 类别id
     */
    private Integer productCategoryId;

    /**
     * 是否上架  1上架 0 下架
     */
    private Integer productIsUp;

    /**
     * 产品数量
     */
    private Integer productCount;

    /**
     * 概述图片
     */
    @TableField(exist = false)
    private List<Productimage> singlePic;

    /**
     * 详细图片
     */
    @TableField(exist = false)
    private List<Productimage> detailPic;

    /**
     * 属性值
     */
    @TableField(exist = false)
    private List<Propertyvalue> propvalues;
}

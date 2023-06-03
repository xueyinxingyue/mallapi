package com.xueyin.mallapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产品属性管理表
 * </p>
 *
 * @author xueyin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Propertyvalue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "propertyvalue_id", type = IdType.AUTO)
    private Integer propertyvalueId;

    /**
     * 属性值
     */
    private String propertyvalueValue;

    /**
     * 关联属性id
     */
    private Integer propertyvaluePropertyId;

    /**
     * 关联产品id
     */
    private Integer propertyvalueProductId;


}

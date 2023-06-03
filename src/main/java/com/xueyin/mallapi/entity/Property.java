package com.xueyin.mallapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类别属性表
 * </p>
 *
 * @author xueyin
 * @since 2023-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "property_id", type = IdType.AUTO)
    private Integer propertyId;

    /**
     * 属性名称
     */
    private String propertyName;

    /**
     * 关联类别id
     */
    private Integer propertyCategoryId;


}

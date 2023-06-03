package com.xueyin.mallapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类别表
 * </p>
 *
 * @author xueyin
 * @since 2023-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

    /**
     * 类别名称
     */
    private String categoryName;

    //拥有的属性信息
    @TableField(exist = false)
    private List<Property> props;
}

package com.xueyin.mallapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产品图片表
 * </p>
 *
 * @author xueyin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Productimage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "productimage_id", type = IdType.AUTO)
    private Integer productimageId;

    /**
     * 类型(0:概述图片 1:详情图片)
     */
    private Integer productimageType;

    /**
     * 图片地址
     */
    private String productimageSrc;

    /**
     * 产品id
     */
    private Integer productimageProductId;


}

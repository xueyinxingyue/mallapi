package com.xueyin.mallapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 地址表
 * </p>
 *
 * @author xueyin
 * @since 2023-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 地区编码
     */
    @TableId(value = "address_areaId", type = IdType.AUTO)
    private String addressAreaid;

    /**
     * 省市名称
     */
    private String addressName;

    /**
     * 父级省市id
     */
    @TableField("address_regionId")
    private String addressRegionid;


}

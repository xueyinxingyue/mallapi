package com.xueyin.mallapi.qo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductQo extends QueryObject{
    private String productName;
    private Integer categoryId;
    private Integer isUp;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}

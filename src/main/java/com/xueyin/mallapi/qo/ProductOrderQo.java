package com.xueyin.mallapi.qo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductOrderQo extends QueryObject{
    private String keyword;
    private List<String> orderStatus;
}

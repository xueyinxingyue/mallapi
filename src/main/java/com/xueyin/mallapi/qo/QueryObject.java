package com.xueyin.mallapi.qo;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装分页信息
 */
@Getter
@Setter
public class QueryObject {
    private int pageNum;
    private int pageSize;
}

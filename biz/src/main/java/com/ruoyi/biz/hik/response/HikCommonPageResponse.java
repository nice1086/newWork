package com.ruoyi.biz.hik.response;

import lombok.Data;

@Data
public class HikCommonPageResponse<T> {
    private Integer total;
    private Integer pageNo;
    private Integer pageSize;
    private T list;
}

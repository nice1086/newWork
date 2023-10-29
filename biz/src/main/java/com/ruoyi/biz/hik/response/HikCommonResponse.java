package com.ruoyi.biz.hik.response;

import lombok.Data;

/**
 * 海康公共返回报文
 * @param <T>
 */
@Data
public class HikCommonResponse<T> {
    private String code;
    private String msg;
    private T data;
}

package com.ruoyi.biz.hik.request;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class AlarmCarQueryRequest {
    private String searchKey;
    /**
     *查询开始时间
     */
    private ZonedDateTime startTime;
    /**
     *查询结束时间
     */
    private ZonedDateTime endTime;
    /**
     *目标页码
     */
    private Integer pageNo = 1;
    /**
     *每页记录数
     */
    private Integer pageSize = 20;

    private String regionIndexCode;
}

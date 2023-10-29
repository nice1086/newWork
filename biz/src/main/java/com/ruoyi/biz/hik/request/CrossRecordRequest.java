package com.ruoyi.biz.hik.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
public class CrossRecordRequest {
    /**
     *停车库唯一标识，可从获取停车库列表接口获取返回参数parkIndexCode，
     */
    private String parkSyscode;
    /**
     *出入口唯一标识，可从获取出入口列表接口获取返回参数entranceIndexCode，
     */
    private String entranceSyscode;
    /**
     *车牌号
     */
    private String plateNo;
    /**
     *卡号
     */
    private String cardNo;
    /**
     *查询开始时间
     */
    private ZonedDateTime startTime;
    /**
     *查询结束时间
     */
    private ZonedDateTime endTime;
    /**
     *进出场标识，0-进场
     * 1-出场
     */
    private Integer vehicleOut;
    /**
     *车辆类型，
     * 0：其他车
     * 1：小型车
     * 2：大型车
     * 3：摩托车
     */
    private Integer vehicleType;

    /**
     *放行结果，0-未放行
     * 1-正常放行
     * 2-离线放行
     */
    private Integer releaseResult;
    /**
     *放行方式，10-未开闸
     * 11-自动开闸
     * 12-人工/人工开闸
     * 13-遥控器开闸
     */
    private Integer releaseWay;
    /**
     *放行原因，100-固定车自动放行
     * 101-临时车自动放行
     * 102-预约车自动放行
     * 103-一户多车自动放行
     */
    private Integer releaseReason;
    /**
     *车辆分类，9-黑名单
     * 10-固定车
     * 11-临时车
     * 12-预约车
     * 14-特殊车
     */
    private String carCategory;
    /**
     *目标页码
     */
    private Integer pageNo = 1;
    /**
     *每页记录数
     */
    private Integer pageSize = 20;
}

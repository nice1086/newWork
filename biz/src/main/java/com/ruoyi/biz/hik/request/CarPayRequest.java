package com.ruoyi.biz.hik.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@ApiModel(value="车辆充值", description="海康")
public class CarPayRequest {
    /**
     *停车库唯一标识，可从获取停车库列表接口获取返回参数parkIndexCode，
     */
    private String parkSyscode;
    /**
     *车牌号
     */
    private String plateNo;

    /**
     *查询开始时间
     */
    private ZonedDateTime startTime;
    /**
     *查询结束时间
     */
    private ZonedDateTime endTime;
    
    /**
     *包期费用，单位:元，2位小数
     */
    private String fee;
    
    

}

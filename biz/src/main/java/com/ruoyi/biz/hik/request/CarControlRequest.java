package com.ruoyi.biz.hik.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@ApiModel(value="车辆布控请求", description="海康，黑名单新增用")
public class CarControlRequest {
    /**
     *车牌号
     */
    private String plateNo;
    
    /**
     *卡号
     */
    private String cardNo;

    /**
     *驾驶员名称
     */
    private String driver;
    
    /**
     *驾驶员电话
     */
    private String driverPhone;

    /**
     *备注信息
     */
    private String remark;
    
    /**
     *布控结束时间,ISO8601格式：
     * yyyy-MM-ddTHH:mm:ss+当前时区，例如北京时间：
     * 2018-07-26T15:00:00+08:00
     */
    private ZonedDateTime endTime;
    
    /**
     *区域IndexCode，根据查询区域列表v2接口获取返回报文中的indexCode字段
     */
    private String regionIndexCode;
    
    

}

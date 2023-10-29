package com.ruoyi.biz.hik.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="车辆布控", description="海康，黑名单取消用")
public class CarNotControlRequest {
    /**
     *布控车辆唯一标识集合(编号间用‘,’分隔)，从车辆布控接口获取返回参数alarmSyscode
     */
    private String alarmSyscodes;
    
    

}

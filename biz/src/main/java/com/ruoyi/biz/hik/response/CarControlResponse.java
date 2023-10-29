package com.ruoyi.biz.hik.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="车辆布控响应", description="海康，黑名单新增用")
public class CarControlResponse {
    
    
    /**
     *布控车辆唯一标识
     */
    private String alarmSyscode;
    
    /**
     *车牌号
     */
    private String plateNo;
    
    /**
     *卡号
     */
    private String cardNo;
    
    

}

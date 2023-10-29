package com.ruoyi.biz.hik.response;

import lombok.Data;

import java.sql.Date;

@Data
public class AlarmCarQueryResponse {
    /**
     * 布控唯一标识
     */
    private String alarmSyscode;
    /**
     * 车牌号码
     */
    private String plateNo;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 驾驶员名称
     */
    private String driver;
    /**
     * 驾驶员电话
     */
    private String driverPhone;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 布控结束时间,
     */
    private String endTime;
}

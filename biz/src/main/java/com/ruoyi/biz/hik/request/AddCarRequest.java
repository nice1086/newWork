package com.ruoyi.biz.hik.request;

import lombok.Data;

@Data
public class AddCarRequest {
    /**
     *调用方指定标识，接口执行成功后将服务端生成的标识与此标识绑定后返回，再通过返回值中的clientId判断哪些成功，哪些失败。所以建议每次接口调用，clientid保持唯一。 注：clientid只对本次调用有效
     *（必填）
     */
    private Integer clientId;
    /**
     *车牌号(必填)
     */
    private String plateNo;
    /**
     *人员ID
     */
    private String personId;
    /**
     *车牌类型
     */
    private String plateType;
    /**
     *车牌颜色
     */
    private String plateColor;

    /**
     *车辆类型，
     * 0：其他车
     * 1：小型车
     * 2：大型车
     * 3：摩托车
     */
    private Integer vehicleType;
    
    /**
     *车辆描述
     */
    private String description;
}

package com.ruoyi.biz.hik.response;

import lombok.Data;

@Data
public class CrossRecordResponse {
    /**
     * 过车记录唯一标识
     */
    private String crossRecordSyscode;
    /**
     * 停车库唯一标识
     */
    private String parkSyscode;
    /**
     * 停车库名称
     */
    private String parkName;
    /**
     * 出入口唯一标识
     */
    private String entranceSyscode;
    /**
     * 出入口名称
     */
    private String entranceName;
    /**
     * 车道唯一标识
     */
    private String roadwaySyscode;
    /**
     * 车道名称
     */
    private String roadwayName;
    /**
     * 是否出场
     * 0-进场，1-出场
     */
    private Integer vehicleOut;

    /**
     * 放行模式，0-禁止放行，1-固定车包期，2-临时车入场，3-预约车入场，10-离线出场，11-缴费出场，12-预付费出场，
     * 13-免费出场，30- 非法卡不放行，31-手动放行，32-特殊车辆放行，33-节假日放行，35-群组放行，36-遥控器开闸
     */
    private Integer releaseMode;
    /**
     * 放行结果，0-未放行
     * 1-正常放行
     * 2-离线放行
     */
    private Integer releaseResult;
    /**
     * 放行方式，10-未开闸
     * 11-自动开闸
     * 12-人工/人工开闸
     * 13-遥控器开闸
     */
    private Integer releaseWay;
    /**
     * 放行原因，100-固定车自动放行
     * 101-临时车自动放行
     * 102-预约车自动放行
     * 103-一户多车自动放行
     */
    private Integer releaseReason;
    /**
     * 车牌号码（最大长度16）
     */
    private String plateNo;
    /**
     * 卡片号码（最大长度32）
     */
    private String cardNo;
    /**
     * 车辆颜色0：其他颜色；
     *             1：白色；
     *             2：银色；
     *             3：灰色；
     *             4：黑色；
     *             5：红色；
     *             6：深蓝色；
     *             7：蓝色；
     *             8：黄色；
     *             9：绿色；
     *             10：棕色；
     *             11：粉色；
     *             12：紫色’
     */
    private Integer vehicleColor;

    /**
     * 车辆类型0：其他车；
     *             1：小型车；
     *             2：大型车；
     *             3：摩托车
     */
    private Integer vehicleType;

    /**
     * 车牌颜色，0:蓝色,1:黄色,2:白色,3:黑色,4:绿色,5:民航黑色, 255:其他颜色
     */
    private Integer plateColor;
    /**
     * 车牌类型，0:标准民用车,1:02式民用车,2:武警车,3:警车,4:民用车双行尾牌车,5:使馆车,6:农用车,7:摩托车,8:新能源车
     */
    private Integer plateType;
    /**
     * 车辆分类
     */
    private String carCategory;
    /**
     * 车辆分类名称
     */
    private String carCategoryName;
    /**
     * 车辆图片uri（最大长度256）
     */
    private String vehiclePicUri;
    /**
     * 车牌图片uri（最大长度256）
     */
    private String plateNoPicUri;
    /**
     * 人脸图片uri（最大长度256）
     */
    private String facePicUri;
    /**
     * 图片服务唯一标识（最大长度64）
     */
    private String aswSyscode;
    /**
     * 通过时间ISO8601格式：
     */
    private String crossTime;
    /**
     * 创建时间
     */
    private String createTime;
}

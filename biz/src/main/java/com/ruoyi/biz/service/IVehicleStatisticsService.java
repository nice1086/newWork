package com.ruoyi.biz.service;

import java.util.Date;


/**
 * 车辆统计Service接口
 *
 * @author 蒋财鸣
 * @date 2023-07-20
 */
public interface IVehicleStatisticsService {

    /**
     * 统计今日车辆数
     *
     * @return 车辆数目
     */
    int selectVehicleCounts();

    /**
     * 统计今日危化品车辆数
     *
     * @return 危化品车辆数目
     */
    int selectHazardousChemicalVehicleCounts();


    /**
     * 统计本月车辆数
     *
     * @return 本月车辆数目
     */
    int selectVehicleCountsOnMonth();
}

package com.ruoyi.biz.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
 * 车辆统计Mapper接口
 *
 * @author 蒋财鸣
 * @date 2023-07-20
 */
@Mapper
public interface VehicleStatisticsMapper {

    /**
     * 统计今日车辆数
     *
     * @param date 时间
     * @return 车辆数目
     */
    int selectVehicleCounts(Date date);

    /**
     * 统计今日危化品车辆数
     *
     * @param date 时间
     * @return 危化品车辆数目
     */
    int selectHazardousChemicalVehicleCounts(Date date);

    /**
     * 统计本月车辆数
     *
     * @param date 时间
     * @return 本月车辆数目
     */
    int selectVehicleCountsOnMonth(Date date);


}

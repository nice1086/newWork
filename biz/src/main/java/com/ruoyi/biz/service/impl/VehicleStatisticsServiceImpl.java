package com.ruoyi.biz.service.impl;

import com.ruoyi.biz.mapper.VehicleStatisticsMapper;
import com.ruoyi.biz.service.IVehicleStatisticsService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VehicleStatisticsServiceImpl implements IVehicleStatisticsService {

    @Autowired
    private VehicleStatisticsMapper statisticsMapper;

    /**
     * 统计今日车辆数
     *
     * @return 车辆数目
     */
    @Override
    public int selectVehicleCounts() {
        return statisticsMapper.selectVehicleCounts(DateUtils.getNowDate());
    }

    /**
     * 统计今日危化品车辆数
     *
     * @return 危化品车辆数目
     */
    @Override
    public int selectHazardousChemicalVehicleCounts() {
        return statisticsMapper.selectHazardousChemicalVehicleCounts(DateUtils.getNowDate());
    }

    /**
     * 统计本月车辆数
     *
     * @return 本月车辆数目
     */
    @Override
    public int selectVehicleCountsOnMonth() {
        return statisticsMapper.selectVehicleCountsOnMonth(DateUtils.getNowDate());
    }
}

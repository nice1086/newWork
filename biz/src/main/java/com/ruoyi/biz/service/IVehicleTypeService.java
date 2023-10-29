package com.ruoyi.biz.service;

import java.util.Map;

public interface IVehicleTypeService {
    Map<String, Object> selectType1(Integer vehicle_type);

    Map<String, Object> selectType2(Integer vehicle_type);

    Map<String, Object> selectType3(Integer vehicle_type);

    Map<String, Object> selectType4(String startDateStr, String endDateStr, Integer vehicle_type);
}

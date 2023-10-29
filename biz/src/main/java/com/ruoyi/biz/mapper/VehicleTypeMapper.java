package com.ruoyi.biz.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface VehicleTypeMapper {
    List<Map<String, Object>> selectType1(Integer vehicle_type);

    List<Map<String, Object>> selectType2(Integer vehicle_type);

    List<Map<String, Object>> selectType3(Integer vehicle_type);

    List<Map<String, Object>> selectType4(@Param("start_time") String startDateStr,
                                          @Param("end_time") String endDateStr,
                                          @Param("vehicle_type") Integer vehicle_type);
}

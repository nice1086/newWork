package com.ruoyi.biz.mapper;

import java.util.List;
import java.util.Map;

public interface VehicleAllMapper {
    List<Map<String, Object>> selectAll(String day_type);

}

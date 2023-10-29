package com.ruoyi.biz.service.impl;

import com.ruoyi.biz.mapper.VehicleAllMapper;
import com.ruoyi.biz.service.IVehicleAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VehicleAllServiceImpl implements IVehicleAllService {

    @Autowired
    private VehicleAllMapper vehicleAllMapper;
    @Autowired
    private VehicleTypeServiceImpl vehicleTypeServiceImpl;

    @Override
    public Map<String, Object> selectAll(String day_type) {
        List<Map<String, Object>> resultList = vehicleAllMapper.selectAll(day_type);
        return vehicleTypeServiceImpl.returnResponse(resultList);
    }

}

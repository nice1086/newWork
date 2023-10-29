package com.ruoyi.biz.service.impl;

import com.ruoyi.biz.mapper.VehicleTypeMapper;
import com.ruoyi.biz.service.IVehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehicleTypeServiceImpl implements IVehicleTypeService {

    @Autowired
    private VehicleTypeMapper vehicleTypeMapper;
    @Override
    public Map<String, Object> selectType1(Integer vehicle_type) {
        List<Map<String, Object>> resultList = vehicleTypeMapper.selectType1(vehicle_type);
        return returnResponse(resultList);
    }

    @Override
    public Map<String, Object> selectType2(Integer vehicle_type) {
        List<Map<String, Object>> resultList = vehicleTypeMapper.selectType2(vehicle_type);
        return returnResponse(resultList);
    }

    @Override
    public Map<String, Object> selectType3(Integer vehicle_type) {
        List<Map<String, Object>> resultList = vehicleTypeMapper.selectType3(vehicle_type);
        return returnResponse(resultList);
    }

    @Override
    public Map<String, Object> selectType4(String startDateStr, String endDateStr, Integer vehicle_type) {
        List<Map<String, Object>> resultList = vehicleTypeMapper.selectType4(startDateStr,endDateStr,vehicle_type);
        return returnResponse(resultList);
    }
    public Map<String, Object> returnResponse(List<Map<String, Object>> resultList){


        List<Long> ydata1 = new ArrayList<>();
        List<Long> ydata2 = new ArrayList<>();
        List<String> xdata = new ArrayList<>();

        for (Map<String,Object> resultMap:resultList){
            ydata1.add((Long) resultMap.get("isout"));
            ydata2.add((Long) resultMap.get("isin"));
            xdata.add((String) resultMap.get("cross_time"));
        }
        Map<String,Object> response = new HashMap<>();
        response.put("ydata1",ydata1);
        response.put("ydata2",ydata2);
        response.put("xdata",xdata);

        return response;
    }
}

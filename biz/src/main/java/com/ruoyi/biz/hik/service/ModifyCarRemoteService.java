package com.ruoyi.biz.hik.service;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.ruoyi.biz.hik.request.AddCarRequest;
import com.ruoyi.biz.hik.request.CarPayRequest;
import com.ruoyi.biz.hik.response.HikCommonPageResponse;
import com.ruoyi.biz.hik.response.HikCommonResponse;
import com.ruoyi.biz.hik.utils.HikClient;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.system.service.ISysDictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ModifyCarRemoteService {
    @Autowired
    private ISysDictTypeService dictTypeService;

    public boolean modifyVehicle(String carNum, ZonedDateTime startTime, ZonedDateTime endTime) {
        // 报文地址
        String url = "/api/pms/v1/car/charge/deletion";
        // 拼装报文
        AddCarRequest addCarRequest = new AddCarRequest();
        addCarRequest.setPlateNo(carNum.trim());
        log.info("HIK取消车辆包期请求{}", addCarRequest);
        // 发送报文
        String response =
                HikClient.callPostStringWithHeadApi(url, JSONObject.toJSONString(addCarRequest));
        HikCommonResponse<HikCommonPageResponse<List<AddCarRequest>>> cancel = JSONObject.parseObject(response,
                new TypeReference<HikCommonResponse<HikCommonPageResponse<List<AddCarRequest>>>>() {
                });
        log.info("HIK取消车辆包期返回{}", cancel);

        if (!"0".equals(cancel.getCode()) && !CharSequenceUtil.equals("0x00072202", cancel.getCode())) {
            log.error("HIK取消车辆包期请求出错{}", cancel.getMsg());
            return false;
        }

        // 车辆充值接口
        url = "/api/pms/v1/car/charge";
        // 拼装报文
        CarPayRequest carPayRequest = new CarPayRequest();
        carPayRequest.setPlateNo(carNum);
        List<SysDictData> parkSyscode = dictTypeService.selectDictDataByType("park_syscode");
        carPayRequest.setParkSyscode(parkSyscode.get(0).getDictLabel());
        carPayRequest.setStartTime(startTime);
        carPayRequest.setEndTime(endTime);
        carPayRequest.setFee("0");
        // 发送报文
        log.info("HIK车辆充值请求{}", carPayRequest);
        String responsePay = HikClient.callPostStringWithHeadApi(url, JSON.toJSONString(carPayRequest, "iso8601"));
        HikCommonResponse<HikCommonPageResponse<List<AddCarRequest>>> dataPay = JSONObject.parseObject(responsePay,
                new TypeReference<HikCommonResponse<HikCommonPageResponse<List<AddCarRequest>>>>() {
                });
        log.info("HIK车辆充值返回{}", dataPay);
        if ("0".equals(dataPay.getCode())) {
            return true;
        }
        log.info("HIK车辆充值出错{}", dataPay.getMsg());
        return false;
    }
}

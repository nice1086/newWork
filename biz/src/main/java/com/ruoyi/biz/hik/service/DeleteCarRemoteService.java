package com.ruoyi.biz.hik.service;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.ruoyi.biz.hik.request.AddCarRequest;
import com.ruoyi.biz.hik.response.HikCommonPageResponse;
import com.ruoyi.biz.hik.response.HikCommonResponse;
import com.ruoyi.biz.hik.utils.HikClient;
import com.ruoyi.system.service.ISysDictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class DeleteCarRemoteService {
    @Autowired
    private ISysDictTypeService dictTypeService;

    public boolean deleteVehicle(String carNum) {
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
        if(CharSequenceUtil.equals("0x00072202", cancel.getCode())){
            log.info("HIK取消车辆包期，返回车辆不存在，车牌号{}", carNum);
            return true;
        }
        if (!"0".equals(cancel.getCode())) {
            log.error("HIK取消车辆包期请求出错{}", cancel.getMsg());
            return false;
        }
        return true;
    }
}

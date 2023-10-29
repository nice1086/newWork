package com.ruoyi.biz.hik.service;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;
import com.ruoyi.biz.hik.request.CarControlRequest;
import com.ruoyi.biz.hik.request.CarNotControlRequest;
import com.ruoyi.biz.hik.response.CarControlResponse;
import com.ruoyi.biz.hik.response.HikCommonResponse;
import com.ruoyi.biz.hik.utils.HikClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;

/**
 * 车辆黑名单相关
 * 海康
 */
@Service
@Slf4j
public class CarBlackRemoteService {
    
    /**
     * 车辆布控
     *
     */
    public String addCarBlack(String carNum, ZonedDateTime endTime){
        // 报文地址
        String url = "/api/pms/v1/alarmCar/addition";
        // 拼装报文
        CarControlRequest carControlRequest = new CarControlRequest();
        carControlRequest.setPlateNo(carNum.trim());
        carControlRequest.setEndTime(endTime);
        if(ObjectUtil.isNotEmpty(carControlRequest)){
            // 发送报文
            log.info("HIK车辆布控请求{}", carControlRequest);
            String response = HikClient.callPostStringWithHeadApi(url, JSON.toJSONString(carControlRequest, "iso8601"));
            HikCommonResponse<CarControlResponse> data = JSONObject.parseObject(response,
                    new TypeReference<HikCommonResponse<CarControlResponse>>(){});
            log.info("HIK车辆布控返回{}", data);
            if("0".equals(data.getCode())){
                return  data.getData().getAlarmSyscode();
            }
            log.info("HIK车辆布控出错{}", data.getMsg());
        }
        return null;
    }
    /**
     * 取消车辆布控
     *
     */
    public boolean delCarBlack(String alarmSyscode){
        // 报文地址
        String url = "/api/pms/v1/alarmCar/deletion";
        // 拼装报文
        CarNotControlRequest carNotControlRequest = new CarNotControlRequest();
        carNotControlRequest.setAlarmSyscodes(alarmSyscode);
        if(ObjectUtil.isNotEmpty(carNotControlRequest)){
            // 发送报文
            log.info("HIK取消车辆布控请求{}", carNotControlRequest);
            String response = HikClient.callPostStringApi(url, JSONObject.toJSONString(carNotControlRequest));
            HikCommonResponse<CarNotControlRequest> data = JSONObject.parseObject(response,
                    new TypeReference<HikCommonResponse<CarNotControlRequest>>(){});
            log.info("HIK取消车辆布控返回{}",data);
            if("0".equals(data.getCode())){
                return true;
            }
            if(CharSequenceUtil.equals("0x00072202", data.getCode())){
                // 车辆资源不存在
                log.info("删除海康车辆，返回车辆不存在，车辆识别码{}", alarmSyscode);
                return true;
            }
            log.error("HIK取消车辆布控出错{}",data.getMsg());
        }
        return false;
    }
}

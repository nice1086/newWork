package com.ruoyi.biz.hik.service;

import com.alibaba.fastjson2.*;
import com.ruoyi.biz.hik.request.CrossRecordRequest;
import com.ruoyi.biz.hik.response.CrossRecordResponse;
import com.ruoyi.biz.hik.response.HikCommonPageResponse;
import com.ruoyi.biz.hik.response.HikCommonResponse;
import com.ruoyi.biz.hik.utils.HikClient;
import com.ruoyi.biz.mapper.CrossRecordMapper;
import com.ruoyi.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * 唐野
 * 2023年7月19日
 * 过车记录同步
 */
@Service
@Slf4j
public class CrossRecordRemoteService {

    @Autowired
    private CrossRecordMapper crossRecordMapper;


    /**
     * 调用同步车辆记录的接口
     * @return
     */
    public boolean synchronizeCrossCars(){
        ZonedDateTime startTime = ZonedDateTime.now().minusMinutes(6);
        ZonedDateTime endTime = ZonedDateTime.now();
        return sendCrossCars(1000, 1, startTime, endTime);
    }

    private boolean sendCrossCars(int pageSize, int page, ZonedDateTime startTime, ZonedDateTime endTime){
        // 报文地址
        String url = "/api/pms/v1/crossRecords/page";
        // 拼装报文
        CrossRecordRequest crossRecordRequest = new CrossRecordRequest();
        crossRecordRequest.setPageSize(pageSize);
        crossRecordRequest.setStartTime(startTime);
        crossRecordRequest.setEndTime(endTime);
        // 发送报文
        log.info("HIK获取车流量请求{}", crossRecordRequest);
        String response = HikClient.callPostStringApi(url, JSON.toJSONString(crossRecordRequest, "iso8601"));
        log.info("HIK获取车流量响应{}", crossRecordRequest);
        JSONObject jsonObject = JSONObject.parseObject(response);
        if(!jsonObject.get("code").equals("0")){
            log.error("HIK获取车流量失败，{}", jsonObject.get("msg"));
            return false;
        }
        HikCommonResponse<HikCommonPageResponse<List<CrossRecordResponse>>> data = JSONObject.parseObject(response,
                new TypeReference<HikCommonResponse<HikCommonPageResponse<List<CrossRecordResponse>>>>(){});
        // 存数据库
        List<CrossRecordResponse> list = data.getData().getList();
        list.forEach(item -> crossRecordMapper.insertRecord(item)
        );
        // 判断翻页
        if(data.getData().getTotal() > pageSize*page){
            // 需要翻页
            sendCrossCars(pageSize, page+1, startTime, endTime);
        }
        return true;
    }
}

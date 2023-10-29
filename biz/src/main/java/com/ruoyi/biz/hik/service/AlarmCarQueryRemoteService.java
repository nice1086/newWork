package com.ruoyi.biz.hik.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.ruoyi.biz.hik.request.AlarmCarQueryRequest;
import com.ruoyi.biz.hik.request.CrossRecordRequest;
import com.ruoyi.biz.hik.response.AlarmCarQueryResponse;
import com.ruoyi.biz.hik.response.CrossRecordResponse;
import com.ruoyi.biz.hik.response.HikCommonPageResponse;
import com.ruoyi.biz.hik.response.HikCommonResponse;
import com.ruoyi.biz.hik.utils.HikClient;
import com.ruoyi.biz.service.IBlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * 唐野 2023年7月20日
 * 布控车辆查询服务
 */
@Service
@Slf4j
public class AlarmCarQueryRemoteService {

    @Autowired
    private IBlacklistService blacklistService;

    public boolean syncBlackList(int pageSize, int page){
        syncBlackList(pageSize, page, null, null, true);
        // 处理 状态为2的改为1已解除
        blacklistService.setReadyToRelease();
        return true;
    }
    public boolean syncBlackList(int pageSize, int page, ZonedDateTime startTime, ZonedDateTime endTime, boolean first){
        // 报文地址
        String url = "/api/pms/v1/alarmCar/page";
        // 拼装报文
        AlarmCarQueryRequest alarmCarQueryRequest = new AlarmCarQueryRequest();
        alarmCarQueryRequest.setPageSize(pageSize);
        alarmCarQueryRequest.setStartTime(startTime);
        alarmCarQueryRequest.setEndTime(endTime);
        // 发送报文
        log.info("HIK获取管控车辆请求{}", alarmCarQueryRequest);
        String response = HikClient.callPostStringApi(url, JSON.toJSONString(alarmCarQueryRequest, "iso8601"));
        log.info("HIK获取管控车辆响应{}", alarmCarQueryRequest);
        JSONObject jsonObject = JSONObject.parseObject(response);
        if(!jsonObject.get("code").equals("0")){
            log.error("HIK获取管控车辆失败，{}", jsonObject.get("msg"));
            return false;
        }
        HikCommonResponse<HikCommonPageResponse<List<AlarmCarQueryResponse>>> data = JSONObject.parseObject(response,
                new TypeReference<HikCommonResponse<HikCommonPageResponse<List<AlarmCarQueryResponse>>>>(){});
        // 存数据库
        List<AlarmCarQueryResponse> list = data.getData().getList();

        if(first){
            // 首先置全部封禁为取消
            blacklistService.setAllBlacklistReady();
        }
        list.forEach(item -> blacklistService.syncBlacklist(item)
        );
        // 判断翻页
        if(data.getData().getTotal() > pageSize*page){
            // 需要翻页
            syncBlackList(pageSize, page+1, startTime, endTime, false);
        }
        return true;
    }
}

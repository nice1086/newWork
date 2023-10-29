package com.ruoyi.biz.hik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 海康同步定时任务
 */
@Service
public class HikTimerService {
    @Autowired
    private CrossRecordRemoteService crossRecordRemoteService;

    @Autowired
    private AlarmCarQueryRemoteService alarmCarQueryRemoteService;

    /**
     * 每隔5分钟获取一次车辆进出情况
     */
    public void syncCrossCar(){
        crossRecordRemoteService.synchronizeCrossCars();
    }

    /**
     * 每天晚上2点进行定时任务同步黑名单
     */
    public void syncBlackCar(){
        alarmCarQueryRemoteService.syncBlackList(1000,1);
    }
}

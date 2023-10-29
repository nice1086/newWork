package com.ruoyi.biz.controller;

import com.ruoyi.biz.hik.service.AlarmCarQueryRemoteService;
import com.ruoyi.biz.hik.service.CrossRecordRemoteService;
import com.ruoyi.biz.hik.service.VehicleAddRemoteService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

/**
 * 车辆Controller
 * 
 * @author yizhi
 * @date 2023-07-13
 */
@RestController
@RequestMapping("/biz/vehicleRecord")
@Api(tags = "海康接口测试")
public class TestVehicleRecordController extends BaseController
{
    @Autowired
    private CrossRecordRemoteService crossRecordRemoteService;

    @Autowired
    private AlarmCarQueryRemoteService alarmCarQueryRemoteService;

    @Autowired
    private VehicleAddRemoteService vehicleAddRemoteService;


    @GetMapping("/sync")
    @ApiOperation("同步车辆进出")
    public String sync()
    {
        crossRecordRemoteService.synchronizeCrossCars();
        return null;
    }
    /**
     * 同步车辆
     */
    @GetMapping("/syncBlack")
    @ApiOperation("同步黑名单车辆")
    public String list()
    {
//        crossRecordRemoteService.synchronizeCrossCars();
        alarmCarQueryRemoteService.syncBlackList(1000, 1);
        return null;
    }

    @GetMapping("/add")
    @ApiOperation("增加车辆7天有效期")
    public TableDataInfo add(String car)
    {
        ZonedDateTime startTime = ZonedDateTime.now();
        ZonedDateTime endTime = ZonedDateTime.now().plusDays(7);
        vehicleAddRemoteService.addVehicle(car, startTime, endTime);
        return null;
    }
}

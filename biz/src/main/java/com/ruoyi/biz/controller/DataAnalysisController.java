package com.ruoyi.biz.controller;

import com.ruoyi.biz.constant.TimeRangeEnum;
import com.ruoyi.biz.domain.BO.EntranceCountSearchBO;
import com.ruoyi.biz.domain.PO.Company;
import com.ruoyi.biz.domain.VO.ColumnarDataVO;
import com.ruoyi.biz.domain.VO.CompanyDataAnalysisVO;
import com.ruoyi.biz.domain.VO.ResidentCompanyVO;
import com.ruoyi.biz.hik.service.HikTimerService;
import com.ruoyi.biz.service.IDataAnalysisService;
import com.ruoyi.biz.service.IResidentCompanyService;
import com.ruoyi.biz.service.IVehicleStatisticsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author MR.ZHAO
 * @since 2023/7/20
 */
@RestController
@RequestMapping("/biz/analysis")
@Api(tags = "数据分析模块")
public class DataAnalysisController extends BaseController {

    @Autowired
    private IDataAnalysisService dataAnalysisService;

    @Autowired
    private IVehicleStatisticsService vehicleStatisticsService;

    @Autowired
    private HikTimerService hikTimerService;

    /**
     * 获取岗亭名称
     */
    @PreAuthorize("@ss.hasPermi('biz:analysis:getEntranceName')")
    @PostMapping("/getEntranceName")
    @ApiOperation("获取岗亭名称")
    public AjaxResult getEntranceName() {
        List<String> entranceName = dataAnalysisService.getEntranceName();
        return success(entranceName);
    }

    /**
     * 获取岗亭车辆统计(以岗亭名称统计)
     */
    @PreAuthorize("@ss.hasPermi('biz:analysis:getEntranceCountName')")
    @PostMapping("/getEntranceCountName")
    @ApiOperation("获取岗亭车辆统计(以岗亭名称统计)")
    public AjaxResult getEntranceCountName(@RequestBody EntranceCountSearchBO searchBO) {
        if (searchBO.getTimeRange() == null) {
            searchBO.setTimeRange(TimeRangeEnum.DAY);
        }
        ColumnarDataVO entranceRealTimeCount = dataAnalysisService.getEntranceCountName(searchBO);
        return success(entranceRealTimeCount);
    }

    /**
     * 获取岗亭车辆统计
     */
    @PreAuthorize("@ss.hasPermi('biz:analysis:getEntranceCount')")
    @PostMapping("/getEntranceCount")
    @ApiOperation("获取岗亭车辆统计")
    public AjaxResult getEntranceCount(@RequestBody @Valid EntranceCountSearchBO searchBO) {
        if (searchBO.getTimeRange() == null) {
            searchBO.setTimeRange(TimeRangeEnum.DAY);
        }
        ColumnarDataVO entranceCount = dataAnalysisService.getEntranceCount(searchBO);
        return success(entranceCount);
    }

    /**
     * 获取企业车辆统计
     */
    @PreAuthorize("@ss.hasPermi('biz:analysis:getCompanyCount')")
    @PostMapping("/getCompanyCount")
    @ApiOperation("获取企业车辆统计")
    public AjaxResult getCompanyTime() {
        List<CompanyDataAnalysisVO> companyCount = dataAnalysisService.getCompanyCount();
        return success(companyCount);
    }

    /**
     * 统计今日车辆
     */
    @PreAuthorize("@ss.hasPermi('biz:analysis:listVehicleOnToday')")
    @GetMapping("/list/vehicle/today")
    @ApiOperation("统计今日车辆")
    public AjaxResult listVehicleOnToday(){
        // 首先调用一次过车同步
        hikTimerService.syncCrossCar();
        return success(vehicleStatisticsService.selectVehicleCounts());
    }

    /**
     * 统计今日危化品车辆
     */
    @PreAuthorize("@ss.hasPermi('biz:analysis:listChemicalVehicle')")
    @GetMapping("/list/vehicle/chemical")
    @ApiOperation("统计今日危化品车辆")
    public AjaxResult listChemicalVehicle() {
        return success(vehicleStatisticsService.selectHazardousChemicalVehicleCounts());
    }


    /**
     * 统计本月车辆
     */
    @PreAuthorize("@ss.hasPermi('biz:analysis:listVehicleOnMonth')")
    @GetMapping("/list/vehicle/month")
    @ApiOperation("统计本月车辆")
    public AjaxResult listVehicleOnMonth(){
        return success(vehicleStatisticsService.selectVehicleCountsOnMonth());
    }
}

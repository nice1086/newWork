package com.ruoyi.biz.controller;

import com.ruoyi.biz.service.IVehicleTypeService;
import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static com.ruoyi.common.core.domain.AjaxResult.success;

@RestController
@RequestMapping("/biz/vehicle/type")
@Api(tags = "统计车辆类型接口")
public class VehicleTypeController {
    @Autowired
    private IVehicleTypeService iVehicleTypeService;

    /**
     * 统计车辆类型
     */
    @PreAuthorize("@ss.hasPermi('biz:vehicle:type:count')")
    @GetMapping("/count")
    @ApiOperation("统计车辆类型")
    public AjaxResult countVlType(HttpServletRequest httpServletRequest){

        Map<String,Object> response = new HashMap<>();
        String startDateStr = StringUtils.defaultIfBlank(httpServletRequest.getParameter("startDate"), null);
        String endDateStr = StringUtils.defaultIfBlank(httpServletRequest.getParameter("endDate"), null);
        Integer vehicle_type = StringUtils.isBlank(httpServletRequest.getParameter("vehicle_type")) ? null : Integer.parseInt(httpServletRequest.getParameter("vehicle_type"));
        String type = StringUtils.defaultIfBlank(httpServletRequest.getParameter("type"), null);


        LocalDate start = null;
        LocalDate end   = null;

//        if(!isValidDate(startDateStr,endDateStr) && !"".equals(startDateStr) && !"".equals(endDateStr)){
//            response.put("list","日期格式错误");
//            return success(response);
//        }
        if(!"".equals(startDateStr) && !"".equals(endDateStr)
           && startDateStr != null && endDateStr != null){
            start = LocalDate.parse(startDateStr);
            end = LocalDate.parse(endDateStr);

            if(ChronoUnit.DAYS.between(start,end) > 999){
                response.put("list","查询范围过大");
                return success(response);
            }
        }

        // 判断是否是今天
        if ("今天".equals(type)) {
            response = iVehicleTypeService.selectType1(vehicle_type);
            return success(response);
        }

        // 判断是否是近三天
        if ("近三天".equals(type)) {
            response = iVehicleTypeService.selectType2(vehicle_type);
            return success(response);
        }

        // 判断是否是近一个月
        if ("近一个月".equals(type)) {
            response = iVehicleTypeService.selectType3(vehicle_type);
            return success(response);
        }
        if(!"".equals(startDateStr) && !"".equals(endDateStr)){
            response = iVehicleTypeService.selectType4(startDateStr,endDateStr,vehicle_type);
            return success(response);
        }
        // 返回默认逻辑
        return success(response);
    }
//  判断日期格式
    public boolean isValidDate(String startDate,String endDate) {
        if(startDate == null || endDate == null){
            return false;
        }
        try {
            LocalDate.parse(startDate);
            LocalDate.parse(endDate);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

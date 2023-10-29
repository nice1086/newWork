package com.ruoyi.biz.controller;

import com.ruoyi.biz.service.IVehicleAllService;
import com.ruoyi.biz.service.IVehicleTypeService;
import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.ruoyi.common.core.domain.AjaxResult.success;

@RestController
@RequestMapping("/biz/vehicle/all")
@Api(tags = "统计总量接口")
public class VehicleAllController {

    @Autowired
    private IVehicleAllService iVehicleAllService;

    /**
     * 统计总量年，月，日
     */
    @PreAuthorize("@ss.hasPermi('biz:vehicle:all:count')")
    @GetMapping("/count")
    @ApiOperation("统计总量")
    public AjaxResult countVlType(String day_type){
        Map<String,Object> response = new HashMap<>();

        if(!"".equals(day_type) && day_type != null){
            response = iVehicleAllService.selectAll(day_type);
            return success(response);
        }

        response.put("list","未选择查询范围，无法查询");
        return success(response);
    }
}

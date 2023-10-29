package com.ruoyi.biz.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.biz.domain.VO.TransportCompanyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.biz.domain.Vehicle;
import com.ruoyi.biz.service.IVehicleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 车辆Controller
 * 
 * @author yizhi
 * @date 2023-07-13
 */
@RestController
@RequestMapping("/biz/vehicle")
@Api(tags = "车辆管理相关接口")
public class VehicleController extends BaseController
{
    @Autowired
    private IVehicleService vehicleService;

    /**
     * 查询车辆列表
     */
    @PreAuthorize("@ss.hasPermi('biz:vehicle:list')")
    @GetMapping("/list")
    @ApiOperation("查询车辆列表")
    public TableDataInfo list(Vehicle vehicle)
    {
        List<Vehicle> list = vehicleService.selectVehicleList(vehicle);
        return getDataTable(list);
    }

    /**
     * 查询运输企业
     */
    @GetMapping("/vehicleCompanyList")
    @ApiOperation("查询运输企业（下拉）")
    public TableDataInfo vehicleCompanyList()
    {
        List<TransportCompanyVO> list = vehicleService.selectVehicleCompanyList();
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('biz:vehicle:importTemplate')")
    @PostMapping("/importTemplate")
    @ApiOperation("导入车辆管理模版下载")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Vehicle> util = new ExcelUtil<Vehicle>(Vehicle.class);
        util.importTemplateExcel(response, "导入车辆模版");
    }

    @PreAuthorize("@ss.hasPermi('biz:vehicle:import')")
    @PostMapping("/importData")
    @ApiOperation("导入车辆管理列表")
    public AjaxResult importData(@RequestPart("file") MultipartFile file, @RequestParam(defaultValue = "false") boolean updateSupport) throws Exception
    {
        ExcelUtil<Vehicle> util = new ExcelUtil<Vehicle>(Vehicle.class);
        List<Vehicle> userList = util.importExcel(file.getInputStream());
        String message = vehicleService.importUser(userList, updateSupport);
        return success(message);
    }

    /**
     * 导出车辆列表
     */
    @PreAuthorize("@ss.hasPermi('biz:vehicle:export')")
    @Log(title = "车辆", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出车辆列表")
    public void export(HttpServletResponse response, Vehicle vehicle)
    {
        List<Vehicle> list = vehicleService.selectVehicleList(vehicle);
        ExcelUtil<Vehicle> util = new ExcelUtil<Vehicle>(Vehicle.class);
        util.exportExcel(response, list, "车辆数据");
    }

    /**
     * 获取车辆详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:vehicle:query')")
    @GetMapping(value = "/{vehicleId}")
    @ApiOperation("获取车辆详细信息")
    public AjaxResult getInfo(@PathVariable("vehicleId") Long vehicleId)
    {
        return success(vehicleService.selectVehicleByVehicleId(vehicleId));
    }

    /**
     * 获取车辆详细信息
     */
    @GetMapping(value = "/selectVehicleOne/{carNum}")
    @ApiOperation("根据车牌号查询车辆详情")
    public AjaxResult selectVehicleOneBycarNum(@PathVariable("carNum") String carNum)
    {
        return success(vehicleService.selectVehicleOneBycarNum(carNum));
    }

    /**
     * 新增车辆
     */
    @PreAuthorize("@ss.hasPermi('biz:vehicle:add')")
    @Log(title = "车辆", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增车辆")
    public AjaxResult add(@RequestBody Vehicle vehicle)
    {
        boolean isImportExcel = false;
        return toAjax(vehicleService.insertVehicle(vehicle,isImportExcel));
    }

    /**
     * 修改车辆
     */
    @PreAuthorize("@ss.hasPermi('biz:vehicle:edit')")
    @Log(title = "车辆", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改车辆")
    public AjaxResult edit(@RequestBody Vehicle vehicle)
    {
        return toAjax(vehicleService.updateVehicle(vehicle));
    }

    /**
     * 删除车辆
     */
    @PreAuthorize("@ss.hasPermi('biz:vehicle:remove')")
    @Log(title = "车辆", businessType = BusinessType.DELETE)
	@DeleteMapping("/{vehicleIds}")
    @ApiOperation("删除车辆")
    public AjaxResult remove(@PathVariable Long[] vehicleIds)
    {
        return toAjax(vehicleService.deleteVehicleByVehicleIds(vehicleIds));
    }
}

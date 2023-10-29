package com.ruoyi.biz.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.biz.domain.BO.TransportCompanySaveBO;
import com.ruoyi.biz.domain.PO.Company;
import com.ruoyi.biz.domain.VO.TransportCompanyVO;
import com.ruoyi.biz.service.IGenCodeService;
import com.ruoyi.biz.service.ITransportCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 承运企业Controller
 * 
 * @author MR.ZHAO
 * @date 2023-07-13
 */
@RestController
@RequestMapping("/biz/transportCompany")
@Api(tags = "承运企业管理模块")
public class TransportCompanyController extends BaseController
{
    @Autowired
    private ITransportCompanyService transportCompanyService;

    @Autowired
    private IGenCodeService genCodeService;

    /**
     * 查询承运企业列表
     */
    @PreAuthorize("@ss.hasPermi('biz:transportCompany:list')")
    @GetMapping("/list")
    @ApiOperation("查询承运企业列表")
    public TableDataInfo list(Company company)
    {
        List<TransportCompanyVO> list = transportCompanyService.selectCompanyList(company);
        return getDataTable(list);
    }

    /**
     * 获取承运企业详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:transportCompany:query')")
    @GetMapping(value = "/{companyId}")
    @ApiOperation("获取承运企业详细信息")
    public AjaxResult getInfo(@PathVariable("companyId") Long companyId)
    {
        return success(transportCompanyService.selectCompanyByCompanyId(companyId));
    }

    /**
     * 新增承运企业
     */
    @PreAuthorize("@ss.hasPermi('biz:transportCompany:add')")
    @Log(title = "承运企业", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增承运企业")
    public AjaxResult add(@RequestBody @Valid TransportCompanySaveBO company)
    {
        return toAjax(transportCompanyService.insertCompany(company));
    }

    /**
     * 修改承运企业
     */
    @PreAuthorize("@ss.hasPermi('biz:transportCompany:edit')")
    @Log(title = "承运企业", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改承运企业")
    public AjaxResult edit(@RequestBody TransportCompanySaveBO company)
    {
        return toAjax(transportCompanyService.updateCompany(company));
    }

    /**
     * 导出承运企业列表
     */
    @PreAuthorize("@ss.hasPermi('biz:transportCompany:export')")
    @Log(title = "承运企业", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出承运企业列表")
    public void export(HttpServletResponse response, Company company)
    {
        List<TransportCompanyVO> list = transportCompanyService.selectCompanyList(company);
        ExcelUtil<TransportCompanyVO> util = new ExcelUtil<>(TransportCompanyVO.class);
        util.exportExcel(response, list, "企业数据");
    }

    /**
     * 导入承运企业列表
     */
    @PreAuthorize("@ss.hasPermi('biz:transportCompany:import')")
    @Log(title = "导入承运企业", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    @ApiOperation("导入承运企业")
    public AjaxResult importData(@RequestPart("file") MultipartFile file, @RequestParam(defaultValue = "false") boolean updateSupport) throws Exception
    {
        ExcelUtil<TransportCompanySaveBO> util = new ExcelUtil<>(TransportCompanySaveBO.class);
        List<TransportCompanySaveBO> dataManagementList = util.importExcel(file.getInputStream());
        String message = transportCompanyService.importData(dataManagementList, updateSupport);
        return success(message);
    }

    /**
     * 获取承运企业模板
     */
    @PreAuthorize("@ss.hasPermi('biz:transportCompany:importTemplate')")
    @PostMapping("/importTemplate")
    @ApiOperation("导入承运企业模板")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<TransportCompanySaveBO> util = new ExcelUtil<>(TransportCompanySaveBO.class);
        util.importTemplateExcel(response, "导入模板");
    }

    /**
     * 获取承运企业代码
     */
    @PreAuthorize("@ss.hasPermi('biz:transportCompany:nextCode')")
    @PostMapping("/nextCode")
    @ApiOperation("获取承运企业代码")
    public AjaxResult nextCode() {
        String nextCode = genCodeService.nextCode(1L);
        return success(nextCode);
    }


    @GetMapping(value = "/selectTransCompany/{orgCode}")
    @ApiOperation("根据机构号查询公司详情")
    public AjaxResult selectVehicleOneBycarNum(@PathVariable("orgCode") String orgCode)
    {
        return success(transportCompanyService.queryTCompanyByOrgCode(orgCode));
    }


}

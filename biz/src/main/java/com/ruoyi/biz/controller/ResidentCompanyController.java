package com.ruoyi.biz.controller;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.biz.domain.BO.ResidentCompanySaveBO;
import com.ruoyi.biz.domain.PO.Company;
import com.ruoyi.biz.domain.VO.ResidentCompanyVO;
import com.ruoyi.biz.service.IGenCodeService;
import com.ruoyi.biz.service.IResidentCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;

import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 驻区企业Controller
 * 
 * @author MR.ZHAO
 * @date 2023-07-13
 */
@RestController
@RequestMapping("/biz/residentCompany")
@Api(tags = "驻区企业管理模块")
public class ResidentCompanyController extends BaseController
{
    @Autowired
    private IResidentCompanyService residentCompanyService;

    @Autowired
    private IGenCodeService genCodeService;

    /**
     * 查询驻区企业列表
     */
    @PreAuthorize("@ss.hasPermi('biz:residentCompany:list')")
    @GetMapping("/list")
    @ApiOperation("查询驻区企业列表")
    public TableDataInfo list(Company company)
    {
        startPage();
        List<ResidentCompanyVO> list = residentCompanyService.selectCompanyList(company);
        return getDataTable(list);
    }

    /**
     * 获取驻区企业详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:residentCompany:query')")
    @GetMapping(value = "/{companyId}")
    @ApiOperation("获取驻区企业详细信息")
    public AjaxResult getInfo(@PathVariable("companyId") Long companyId)
    {
        return success(residentCompanyService.selectCompanyByCompanyId(companyId));
    }

    /**
     * 新增驻区企业
     */
    @PreAuthorize("@ss.hasPermi('biz:residentCompany:add')")
    @Log(title = "驻区企业", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增驻区企业")
    public AjaxResult add(@RequestBody @Valid ResidentCompanySaveBO company)
    {
        return toAjax(residentCompanyService.insertCompany(company));
    }

    /**
     * 修改驻区企业
     */
    @PreAuthorize("@ss.hasPermi('biz:residentCompany:edit')")
    @Log(title = "驻区企业", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改驻区企业")
    public AjaxResult edit(@RequestBody ResidentCompanySaveBO company)
    {
        return toAjax(residentCompanyService.updateCompany(company));
    }

    /**
     * 导出驻区企业列表
     */
    @PreAuthorize("@ss.hasPermi('biz:residentCompany:export')")
    @Log(title = "驻区企业", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出驻区企业列表")
    public void export(HttpServletResponse response, Company company)
    {
        List<ResidentCompanyVO> list = residentCompanyService.selectCompanyList(company);
        ExcelUtil<ResidentCompanyVO> util = new ExcelUtil<>(ResidentCompanyVO.class);
        util.exportExcel(response, list, "驻区企业");
    }

    /**
     * 导入驻区企业列表
     */
    @PreAuthorize("@ss.hasPermi('biz:residentCompany:import')")
    @Log(title = "导入驻区企业", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    @ApiOperation("导入驻区企业")
    public AjaxResult importData(@RequestPart("file") MultipartFile file, @RequestParam(defaultValue = "false") boolean updateSupport) throws Exception
    {
        ExcelUtil<ResidentCompanySaveBO> util = new ExcelUtil<>(ResidentCompanySaveBO.class);
        List<ResidentCompanySaveBO> dataManagementList = util.importExcel(file.getInputStream());
        String message = residentCompanyService.importData(dataManagementList, updateSupport);
        return success(message);
    }

    /**
     * 导入驻区企业模板
     */
    @PreAuthorize("@ss.hasPermi('biz:residentCompany:importTemplate')")
    @PostMapping("/importTemplate")
    @ApiOperation("导入驻区企业模板")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<ResidentCompanySaveBO> util = new ExcelUtil<>(ResidentCompanySaveBO.class);
        util.importTemplateExcel(response, "导入驻区企业模板");
    }

    /**
     * 获取驻区企业代码
     */
    @PreAuthorize("@ss.hasPermi('biz:residentCompany:nextCode')")
    @PostMapping("/nextCode")
    @ApiOperation("获取驻区企业代码")
    public AjaxResult nextCode() {
        String nextCode = genCodeService.nextCode(1L);
        return success(nextCode);
    }

}

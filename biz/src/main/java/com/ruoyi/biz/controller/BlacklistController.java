package com.ruoyi.biz.controller;

import java.net.URLDecoder;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.biz.service.IBlacklistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.biz.domain.PO.Blacklist;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 黑名单Controller
 * 
 * @author yizhi
 * @date 2023-07-13
 */
@RestController
@RequestMapping("/biz/blacklist")
@Api(tags = "黑名单管理模块")
public class BlacklistController extends BaseController
{
    @Autowired
    private IBlacklistService blacklistService;

    /**
     * 查询黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('biz:blacklist:list')")
    @GetMapping("/list")
    @ApiOperation("查询黑名单列表")
    public TableDataInfo list(Blacklist blacklist)
    {
        startPage();
        List<Blacklist> list = blacklistService.selectBlacklistList(blacklist);
        return getDataTable(list);
    }



    /**
     * 导出黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('biz:blacklist:export')")
    @Log(title = "黑名单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出黑名单列表")
    public void export(HttpServletResponse response, Blacklist blacklist)
    {
        List<Blacklist> list = blacklistService.selectBlacklistList(blacklist);
        ExcelUtil<Blacklist> util = new ExcelUtil<Blacklist>(Blacklist.class);
        util.exportExcel(response, list, "黑名单数据");
    }

    /**
     * 获取黑名单详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:blacklist:query')")
    @GetMapping(value = "/{blacklistId}")
    @ApiOperation("获取黑名单详细信息")
    public AjaxResult getInfo(@PathVariable("blacklistId") Long blacklistId)
    {
        return success(blacklistService.selectBlacklistByBlacklistId(blacklistId));
    }

    /**
     * 新增黑名单
     */
    @PreAuthorize("@ss.hasPermi('biz:blacklist:add')")
    @Log(title = "黑名单", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增黑名单")
    public AjaxResult add(@RequestBody Blacklist blacklist)
    {
        if(blacklist.getBlacklistType().equals("车辆") && blacklist.getBlacklistName() == null){
            return AjaxResult.error("请填写车牌号");
        }
        if(blacklist.getBlacklistType().equals("承运企业") && blacklist.getBlacklistName() == null){
            return AjaxResult.error("请填写证件号");
        }
        if(ObjectUtil.isNotEmpty(blacklist.getBlacklistId())){
            // 更新
            return edit(blacklist);
        }
        blacklist.setCreateBy(getUsername());
        return toAjax(blacklistService.insertBlacklist(blacklist));
    }

    /**
     * 修改黑名单
     */
    @PreAuthorize("@ss.hasPermi('biz:blacklist:edit')")
    @Log(title = "黑名单", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改黑名单")
    public AjaxResult edit(@RequestBody Blacklist blacklist)
    {
        blacklist.setUpdateBy(getUsername());
        return toAjax(blacklistService.updateBlacklist(blacklist));
    }

    /**
     * 解除黑名单
     */
    @PreAuthorize("@ss.hasPermi('biz:blacklist:edit')")
    @Log(title = "黑名单", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    @ApiOperation("解除黑名单")
    public AjaxResult changeStatus(@RequestBody Blacklist blacklist)
    {
        blacklist.setUpdateBy(getUsername());
        blacklist.setBlacklistStatus(1L);
        return toAjax(blacklistService.updateBlacklistStatus(blacklist));
    }

    /**
     * 删除黑名单
     */
    @PreAuthorize("@ss.hasPermi('biz:blacklist:remove')")
    @Log(title = "黑名单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{blacklistIds}")
    @ApiOperation("删除黑名单")
    public AjaxResult remove(@PathVariable Long[] blacklistIds)
    {
        return toAjax(blacklistService.deleteBlacklistByBlacklistIds(blacklistIds));
    }

    /**
     * 读取黑名单
     */
    @PostMapping(value = "/readBlacklist")
    @ApiOperation("查询黑名单详情")
    public AjaxResult readBlacklist(@RequestBody Blacklist blacklist) {
        logger.info("接收到黑名单查询请求，请求参数{}", blacklist.getBlacklistName());
        return success(blacklistService.queryBlacklistByCarNum(blacklist.getBlacklistName()));
    }
}

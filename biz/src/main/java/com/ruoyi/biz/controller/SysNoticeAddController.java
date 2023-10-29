package com.ruoyi.biz.controller;

import com.ruoyi.biz.domain.SysNoticeAdd;
import com.ruoyi.biz.domain.SysNoticeDTO;
import com.ruoyi.biz.service.ISysNoticeAddService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.system.mapper.SysRoleMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biz/notification")
@Api(tags = "发布通知接口")
public class SysNoticeAddController extends BaseController {

    @Autowired
    private ISysNoticeAddService iSysNoticeAddService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @ApiOperation("通知发布")
    @PreAuthorize("@ss.hasPermi('biz:notification:add')")
    @Log(title = "通知发布", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult noticeAdd(@Validated @RequestBody SysNoticeDTO sysNoticeDTO) {
        SysNoticeAdd sysNoticeAdd = sysNoticeDTO.getSysNoticeAdd();
        iSysNoticeAddService.noticeAdd(sysNoticeAdd);
        // 查询角色下所有用户
        String roleName = sysNoticeDTO.getRoleName();
        List<Long> longs = sysRoleMapper.selectAllUserByRole(roleName);
        for (Long userId : longs) {
            sysNoticeDTO.setTo_user_id(userId);
            sysNoticeDTO.getSysNoticeAdd().setCreateBy(getUsername());
            sysNoticeDTO.setIs_read(0);
            sysNoticeDTO.setNoticeId(sysNoticeAdd.getNoticeId());
            iSysNoticeAddService.noticeUserAdd(sysNoticeDTO);
        }
        SysUser user = SecurityUtils.getLoginUser().getUser();
        iSysNoticeAddService.deleteDraft(user.getUserName());
        return toAjax(1);
    }

    /**
     * 管理员获取消息详情
     */
    @ApiOperation("查询历史通知信息")
    @PreAuthorize("@ss.hasPermi('biz:notification:details')")
    @GetMapping("/details")
    public TableDataInfo noticeDetails(SysNoticeAdd sysNoticeAdd) {
        startPage();
        return getDataTable(iSysNoticeAddService.selectAdminNotice());
    }


    /**
     * 管理员获取消息详情
     */
    @ApiOperation("管理员获取消息详情")
    @PreAuthorize("@ss.hasPermi('biz:notification:details')")
    @PostMapping("/adminReadDetails")
    public AjaxResult adminReadDetails(@RequestBody SysNoticeAdd sysNoticeAdd) {
        return success(iSysNoticeAddService.adminReadDetail(sysNoticeAdd.getNoticeId()));
    }


    /**
     * 查看我的消息
     */
    @ApiOperation("查看我的消息")
    @PreAuthorize("@ss.hasPermi('biz:notification:my')")
    @PostMapping("/myBox")
    public AjaxResult myNotification() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        startPage();
        return success(iSysNoticeAddService.selectUserNotice(user.getUserId()));
    }


    /**
     * 阅读消息
     */
    @ApiOperation("阅读消息")
    @PreAuthorize("@ss.hasPermi('biz:notification:read')")
    @PostMapping("/read")
    public AjaxResult readMessage(@RequestBody SysNoticeAdd sysNoticeAdd) {
        return success(iSysNoticeAddService.readNotice(sysNoticeAdd.getNoticeUserId()));
    }

    /**
     * 查看消息详情
     *
     * @param sysNoticeAdd
     * @return
     */
    @ApiOperation("查看消息详情")
    @PreAuthorize("@ss.hasPermi('biz:notification:detail')")
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody SysNoticeAdd sysNoticeAdd) {
        return success(iSysNoticeAddService.noticeDetail(sysNoticeAdd.getNoticeUserId()));
    }

    @ApiOperation("未读消息数量")
    @PreAuthorize("@ss.hasPermi('biz:notification:unread')")
    @PostMapping("/unread")
    public AjaxResult unread() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        return success(iSysNoticeAddService.unread(user.getUserId()));
    }


    @ApiOperation("新增草稿")
    @PreAuthorize("@ss.hasPermi('biz:notification:draft:save')")
    @Log(title = "新增草稿", businessType = BusinessType.INSERT)
    @PostMapping("/draft/save")
    public AjaxResult saveDraft(@RequestBody SysNoticeAdd sysNoticeAdd) {
        sysNoticeAdd.setRemark("draft");
        SysUser user = SecurityUtils.getLoginUser().getUser();
        sysNoticeAdd.setCreateBy(user.getUserName());
        // 删除草稿
        iSysNoticeAddService.deleteDraft(user.getUserName());
        return success(iSysNoticeAddService.noticeAdd(sysNoticeAdd));
    }

    @ApiOperation("读取草稿内容")
    @PreAuthorize("@ss.hasPermi('biz:notification:draft:load')")
    @GetMapping("/draft/load")
    public AjaxResult loadDraft() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        return success(iSysNoticeAddService.draftDetail(user.getUserName()));

    }
}

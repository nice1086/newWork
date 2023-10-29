package com.ruoyi.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.biz.domain.SysNoticeAdd;
import com.ruoyi.biz.domain.SysNoticeDTO;
import com.ruoyi.biz.domain.SysNoticeVO;

import java.util.List;


public interface ISysNoticeAddService extends IService<SysNoticeAdd> {
    int noticeAdd(SysNoticeAdd sysNoticeAdd);

    int updateStatus(Long user_id, Long notice_id);

    void noticeUserAdd(SysNoticeDTO sysNoticeDTO);

    List<SysNoticeVO> selectAdminNotice();

    List<SysNoticeVO> selectUserNotice(Long userId);

    int readNotice(Long createBy);

    SysNoticeVO noticeDetail(Long noticeId);

    Long unread(Long userId);

    SysNoticeVO draftDetail(String createBy);

    SysNoticeVO adminReadDetail(Long noticeId);

    int deleteDraft(String createBy);
}

package com.ruoyi.biz.mapper;

import com.ruoyi.biz.domain.SysNoticeAdd;
import com.ruoyi.biz.domain.SysNoticeDTO;
import com.ruoyi.biz.domain.SysNoticeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysNoticeAddMapper {

    int noticeAdd(SysNoticeAdd sysNoticeAdd);

    int updateStatus(@Param("user_id") Long user_id,
                     @Param("notice_id") Long notice_id);

    void noticeUserAdd(@Param("sysNoticeDTO") SysNoticeDTO sysNoticeDTO);

    List<SysNoticeVO> selectAdminNotice();

    List<SysNoticeVO> selectUserNotice(@Param("userId") Long userId);

    int readNotice(@Param("id") Long id);

    SysNoticeVO noticeDetail(@Param("id") Long id);

    Long countUnread(@Param("userId") Long userId);

    SysNoticeVO draftDatail(String createBy);

    SysNoticeVO adminNoticeDetail(Long noticeId);

    int deleteDraft(String createBy);
}

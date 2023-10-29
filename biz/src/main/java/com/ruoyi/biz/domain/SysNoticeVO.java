package com.ruoyi.biz.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysNoticeVO implements Serializable {
    /** 公告ID */
    @TableId(type = IdType.AUTO)
    private Long noticeId;

    /** 公告标题 */
    private String noticeTitle;

    /** 公告类型（1通知 2公告） */
    private String noticeType;

    /** 公告内容 */
    private String noticeContent;

    //    公告状态
    private String status;

    //    创建者
    private String createBy;

    //    创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    //    备注
    private String remark;

    //发送对象
    private Long toUserId;

    //    是否已读 0否 1是
    private int isRead;

   //    接收对象名称
    private String roleName;

    private Long noticeUserId;
}

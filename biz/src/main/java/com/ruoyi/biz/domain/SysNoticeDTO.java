package com.ruoyi.biz.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SysNoticeDTO {
    private SysNoticeAdd sysNoticeAdd;

    /** 公告ID */
    @TableId(type = IdType.AUTO)
    private Long noticeId;

    private Long notice_id;

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
    private Date createTime;

    //    备注
    private String remark;

    //发送对象
    private Long to_user_id;

    //    是否已读 0否 1是
    private int is_read;

    //    接收对象名称
    private String roleName;


    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public SysNoticeAdd getSysNoticeAdd() {
        return sysNoticeAdd;
    }

    public void setSysNoticeAdd(SysNoticeAdd sysNoticeAdd) {
        this.sysNoticeAdd = sysNoticeAdd;
    }

    public Long getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(Long to_user_id) {
        this.to_user_id = to_user_id;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    @Override
    public String toString() {
        return "SysNoticeDTO{" +
                "sysNoticeAdd=" + sysNoticeAdd +
                ", noticeId=" + noticeId +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", noticeType='" + noticeType + '\'' +
                ", noticeContent='" + noticeContent + '\'' +
                ", status='" + status + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                ", to_user_id=" + to_user_id +
                ", is_read=" + is_read +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}

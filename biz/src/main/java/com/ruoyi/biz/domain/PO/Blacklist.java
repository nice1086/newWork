package com.ruoyi.biz.domain.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 黑名单对象 t_blacklist
 * 
 * @author yizhi
 * @date 2023-07-13
 */
@Data
public class Blacklist extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long blacklistId;

    /** 黑名单类型（数据字典） */
    @Excel(name = "黑名单类型", readConverterExp = "数据字典")
    private String blacklistType;

    /** 黑名单内容 */
    @Excel(name = "黑名单内容")
    private String blacklistName;

    /** 黑名单状态：0封停中 1已解除  */
    @Excel(name = "黑名单状态：0封停中 1已解除 ")
    private Long blacklistStatus;

    /** 封停开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "封停开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 封停结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "封停结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 封停原因（数据字段） */
    @Excel(name = "封停原因", readConverterExp = "数=据字段")
    private String reason;

    private Long companyId;

    private String alarmSyscode;

    private Integer blacklistScoop;
}

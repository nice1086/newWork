package com.ruoyi.biz.domain.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author MR.ZHAO
 * @since 2023/7/13
 */
@Data
public class ResidentCompanyVO {

    /** 企业ID */
    private Long companyId;

    /** 企业编码（如：入驻企业B001） */
    @Excel(name = "企业编码")
    @ApiModelProperty(value = "企业编码")
    private String companyCode;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    @ApiModelProperty(value = "统一社会信用代码")
    private String orgCode;

    /** 企业名称 */
    @Excel(name = "企业名称")
    @ApiModelProperty(value = "企业名称")
    private String companyName;

    /** 企业简称 */
    @Excel(name = "企业简称")
    @ApiModelProperty(value = "企业简称")
    private String companyAbbreviation;

    /** 注册地址 */
    @Excel(name = "注册地址")
    @ApiModelProperty(value = "注册地址")
    private String registPlace;

    /** 企业规模（1小型 2大型 见数据字典） */
    @Excel(name = "企业规模", dictType = "company_size")
    @ApiModelProperty(value = "企业规模(数据字典)")
    private Integer companySize;

    /** 入驻企业类型：1无装卸作业企业 2有装卸作业企业 见数据字典 */
    @Excel(name = "入驻企业类型", dictType = "company_type")
    @ApiModelProperty(value = "入驻企业类型（数据字典）")
    private Integer companyType;

    /** 企业法人 */
    @Excel(name = "企业法人")
    @ApiModelProperty(value = "企业法人")
    private String leadingCadre;

    /** 联系电话 */
    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系电话")
    private String telephone;

    /** 联系人 */
    @Excel(name = "联系人")
    @ApiModelProperty(value = "联系人")
    private String contacts;

    /** 联系人手机号 */
    @Excel(name = "联系人手机号")
    @ApiModelProperty(value = "联系人手机号")
    private String mobile;

    /** 联系电子邮箱 */
    @Excel(name = "联系电子邮箱")
    @ApiModelProperty(value = "联系电子邮箱")
    private String email;

    /** 经营范围 */
    @Excel(name = "经营范围")
    @ApiModelProperty(value = "经营范围")
    private String businessScope;

    /** 状态 0启用 -1禁用 */
    @Excel(name = "状态", dictType = "company_status")
    @ApiModelProperty(value = "状态 0启用 -1禁用")
    private String status;

    /** 创建者 */
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /** 更新者 */
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty("添加车队")
    @Excel(name = "添加车队")
    private Integer transportCount;

    @ApiModelProperty("车辆数量")
    @Excel(name = "车辆数量")
    private Integer vehicleCount;

}

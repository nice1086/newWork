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
public class TransportCompanyVO {

    /** 企业ID */
    private Long companyId;

    /** 企业编码（如入驻企业B001 承运D001） */
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

    /** 状态 0启用 -1禁用 */
    @Excel(name = "状态", dictType = "company_status")
    @ApiModelProperty(value = "状态 0启用 -1禁用")
    private String status;

    /** 责任企业id */
    @ApiModelProperty(value = "责任企业id")
    private Long createCompanyId;

    /** 责任企业 */
    @Excel(name = "责任企业")
    @ApiModelProperty(value = "责任企业")
    private String createCompany;

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

    @ApiModelProperty("车辆数量")
    @Excel(name = "车辆数量")
    private Integer vehicleCount;

}

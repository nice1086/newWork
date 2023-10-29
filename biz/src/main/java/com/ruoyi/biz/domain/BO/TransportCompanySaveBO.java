package com.ruoyi.biz.domain.BO;

import com.ruoyi.biz.util.validation.Phone;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author MR.ZHAO
 * @since 2023/7/13
 */
@Data
public class TransportCompanySaveBO {

    /** 企业ID */
    private Long companyId;

    /** 企业编码（如承运D001） */
    @Excel(name = "企业编码")
    @ApiModelProperty(value = "承运企业编码，如：D001")
    @NotBlank(message = "企业编码不能为空")
    private String companyCode;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    @ApiModelProperty(value = "统一社会信用代码")
    @Pattern(regexp = "^[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}$", message = "统一社会信用代码格式错误")
    @NotBlank(message = "统一社会信用代码不能为空")
    private String orgCode;

    /** 企业名称 */
    @Excel(name = "企业名称")
    @ApiModelProperty(value = "企业名称")
    @NotBlank(message = "企业名称不能为空")
    private String companyName;

    /** 企业简称 */
    @Excel(name = "企业简称")
    @ApiModelProperty(value = "企业简称")
    @NotBlank(message = "企业简称不能为空")
    private String companyAbbreviation;

    /** 联系电话 */
    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String telephone;

    /** 联系人 */
    @Excel(name = "联系人")
    @ApiModelProperty(value = "联系人")
    @NotBlank(message = "联系人不能为空")
    private String contacts;

    /** 联系人手机号 */
    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系电话")
    @Phone
    @NotBlank(message = "联系电话不能为空")
    private String mobile;

    /** 状态 0启用 -1禁用 */
    @Excel(name = "状态", dictType = "company_status")
    @ApiModelProperty(value = "状态 0启用 -1禁用")
    private String status;

    /** 密码 */
    @ApiModelProperty(value = "密码")
    @Excel(name = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    private boolean authFlag = false;

}

package com.ruoyi.biz.domain.BO;

import com.ruoyi.biz.util.validation.Phone;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author MR.ZHAO
 * @since 2023/7/13
 */
@Data
public class ResidentCompanySaveBO {

    /** 企业ID */
    private Long companyId;

    /** 企业编码（如入驻企业B001） */
    @Excel(name = "企业编码")
    @ApiModelProperty(value = "企业编码")
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

    /** 注册地址 */
    @Excel(name = "注册地址")
    @ApiModelProperty(value = "注册地址")
    @NotBlank(message = "注册地址不能为空")
    private String registPlace;

    /** 企业规模（1小型 2大型 见数据字典） */
    @Excel(name = "企业规模", dictType = "company_size")
    @ApiModelProperty(value = "企业规模(数据字典)")
    @NotNull(message = "企业规模不能为空")
    private Integer companySize;

    /** 入驻企业类型：1无装卸作业企业 2有装卸作业企业 见数据字典 */
    @Excel(name = "入驻企业类型", dictType = "company_type")
    @ApiModelProperty(value = "入驻企业类型（数据字典）")
    @NotNull(message = "企业类型不能为空")
    private Integer companyType;

    /** 企业法人 */
    @Excel(name = "企业法人")
    @ApiModelProperty(value = "企业法人")
    @NotBlank(message = "企业法人不能为空")
    private String leadingCadre;

    /** 电话 */
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

    /** 联系电子邮箱 */
    @Excel(name = "联系电子邮箱")
    @Email
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

    /** 密码 */
    @ApiModelProperty(value = "密码")
    @Excel(name = "密码")
    @NotBlank(message = "企业登录密码不能为空")
    private String password;

}

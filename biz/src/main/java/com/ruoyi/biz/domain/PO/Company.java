package com.ruoyi.biz.domain.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 企业对象 t_company
 * 
 * @author MR.ZHAO
 * @date 2023-07-13
 */
@TableName(value = "t_company")
@EqualsAndHashCode(callSuper = true)
@Data
public class Company extends BaseEntity
{
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /** 企业ID */
    @TableId(type = IdType.AUTO)
    private Long companyId;

    /** 企业编码（如入驻企业B001 承运D001） */
    @ApiModelProperty(value = "企业编码")
    private String companyCode;

    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码")
    private String orgCode;

    /** 企业名称 */
    @ApiModelProperty(value = "企业名称")
    private String companyName;

    /** 企业简称 */
    @ApiModelProperty(value = "企业简称")
    private String companyAbbreviation;

    /** 注册地址 */
    @ApiModelProperty(value = "注册地址")
    private String registPlace;

    /** 注册类型 1驻区企业 2承运企业 */
    @ApiModelProperty(value = "注册类型 1驻区企业 2承运企业")
    private Integer registeType;

    /** 企业规模（1小型 2大型 见数据字典） */
    @ApiModelProperty(value = "企业规模(数据字典)")
    private Integer companySize;

    /** 入驻企业类型：1无装卸作业企业 2有装卸作业企业 见数据字典 */
    @ApiModelProperty(value = "入驻企业类型（数据字典）")
    private Integer companyType;

    /** 企业法人 */
    @ApiModelProperty(value = "企业法人")
    private String leadingCadre;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话")
    private String telephone;

    /** 联系人 */
    @ApiModelProperty(value = "联系人")
    private String contacts;

    /** 联系人手机号 */
    @ApiModelProperty(value = "联系人手机号")
    private String mobile;

    /** 联系电子邮箱 */
    @ApiModelProperty(value = "联系电子邮箱")
    private String email;

    /** 经营范围 */
    @ApiModelProperty(value = "经营范围")
    private String businessScope;

    /** 状态 0启用 -1禁用 */
    @ApiModelProperty(value = "状态 0启用 -1禁用")
    private String status;

    /** 创建所属企业 */
    @ApiModelProperty(value = "责任企业id")
    private Long createCompanyId;

}

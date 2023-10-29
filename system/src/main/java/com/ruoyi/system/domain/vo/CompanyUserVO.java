package com.ruoyi.system.domain.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="新增企业用户相关")
public class CompanyUserVO {
    /** 企业ID */
    @ApiModelProperty(value="企业ID,所属企业")
    private Long companyId;

    /** 企业编码（如入驻企业B001 承运D001） */
    @Excel(name = "企业编码", readConverterExp = "如=入驻企业B001,承=运D001")
    private String companyCode;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    private String orgCode;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String companyName;

    /** 企业简称 */
    @Excel(name = "企业简称")
    private String companyAbbreviation;

    /** 注册地址 */
    @Excel(name = "注册地址")
    private String registPlace;

    /** 注册类型 1驻区企业 2承运企业 */
    @Excel(name = "注册类型 1驻区企业 2承运企业")
    private Integer registeType;

    /** 企业规模（1小型 2大型 见数据字典） */
    @Excel(name = "企业规模", readConverterExp = "1=小型,2=大型,见=数据字典")
    private Integer companySize;

    /** 入驻企业类型：1无装卸作业企业 2有装卸作业企业 见数据字典 */
    @Excel(name = "入驻企业类型：1无装卸作业企业 2有装卸作业企业 见数据字典")
    private Integer companyType;

    /** 主要负责人 */
    @Excel(name = "主要负责人")
    private String leadingCadre;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String telephone;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contacts;

    /** 联系人手机号 */
    @Excel(name = "联系人手机号")
    private String mobile;

    /** 联系电子邮箱 */
    @Excel(name = "联系电子邮箱")
    private String email;

    /** 经营范围 */
    @Excel(name = "经营范围")
    private String businessScope;

    /** 状态 0启用 -1禁用 */
    @Excel(name = "状态 0启用 -1禁用")
    private String status;

    /** 创建所属企业 */
    @Excel(name = "创建所属企业")
    private Long createCompanyId;


    /** 用户ID */
    @ApiModelProperty(value="用户编号")
    private Long userId;

    /** 部门ID */
    @ApiModelProperty(value="部门编号")
    private Long deptId;


    /** 用户账号 */
    @Excel(name = "登录名称")
    private String userName;

    /** 用户昵称 */
    @Excel(name = "用户名称")
    private String nickName;

    /** 密码 */
    private String password;

}

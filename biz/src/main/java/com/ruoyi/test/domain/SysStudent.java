package com.ruoyi.test.domain;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

import lombok.Data;

/**
 * 学生信息对象 sys_student
 * 
 * @author ruoyi
 */
@Data
@TableName(value = "sys_student")
public class SysStudent implements Serializable
{
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @TableId(type = IdType.AUTO)
    private Long studentId;

    /** 学生名称 */
    @Excel(name = "学生名称")
    private String studentName;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer studentAge;

    /** 爱好（0代码 1音乐 2电影） */
    @Excel(name = "爱好", readConverterExp = "0=代码,1=音乐,2=电影")
    private String studentHobby;

    /** 性别（0男 1女 2未知） */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String studentSex;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String studentStatus;

    /** 生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date studentBirthday;

}

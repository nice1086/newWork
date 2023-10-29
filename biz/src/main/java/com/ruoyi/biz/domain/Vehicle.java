package com.ruoyi.biz.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 车辆对象 t_vehicle
 * 
 * @author yizhi
 * @date 2023-07-13
 */
@Data
public class Vehicle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 车辆ID */
    @TableId(type = IdType.AUTO)
    private Long vehicleId;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String carNum;

    /** 品牌型号 */
    @Excel(name = "品牌型号")
    private String model;

    
    @ApiModelProperty(value = "所属承运企业ID")
    private String companyId;

    /** 企业名称 */
    @TableField(exist = false)
    @ApiModelProperty(value = "企业名称")
    private String companyName;

    /** 状态 0有效 -1过期 */
    @Excel(name = "状态", readConverterExp = "0=有效,-1=过期",dictType = "vehicle_car_status")
    private String status;

    /** 车辆类型(数据字典) */
    @Excel(name = "车辆类型", width = 30, dictType = "vehicle_type")
    private String vehicleType;

    /** 证件逗号隔开 */
    @ApiModelProperty(value = "证件号，逗号隔开")
    private String imgUrl;

    /** 类型 0 长期 1临时 */
    @Excel(name = "类型", readConverterExp = "0=长期,1=临时",dictType = "vehicle_car_type")
    private String type;

    /** 有效开始时间 默认当前 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效开始时间 （格式为：2023-07-01）", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 有效结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效结束时间（格式为：2023-07-21）", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 联系电话 */
    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系电话")
    private String telephone;

    /** 联系人 */
    @Excel(name = "联系人")
    @ApiModelProperty(value = "联系人")
    private String contacts;

    /** 添加者userId */
    private Long userId;

    public void setVehicleId(Long vehicleId) 
    {
        this.vehicleId = vehicleId;
    }

    public Long getVehicleId() 
    {
        return vehicleId;
    }
    public void setCarNum(String carNum) 
    {
        this.carNum = carNum;
    }

    public String getCarNum() 
    {
        return carNum;
    }
    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }
    public void setCompanyId(String companyId) 
    {
        this.companyId = companyId;
    }

    public String getCompanyId() 
    {
        return companyId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setVehicleType(String vehicleType) 
    {
        this.vehicleType = vehicleType;
    }

    public String getVehicleType() 
    {
        return vehicleType;
    }
    public void setImgUrl(String imgUrl) 
    {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() 
    {
        return imgUrl;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("vehicleId", getVehicleId())
            .append("carNum", getCarNum())
            .append("model", getModel())
            .append("companyId", getCompanyId())
            .append("status", getStatus())
            .append("vehicleType", getVehicleType())
            .append("imgUrl", getImgUrl())
            .append("type", getType())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

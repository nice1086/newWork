package com.ruoyi.biz.domain.BO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.biz.constant.TimeRangeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author MR.ZHAO
 * @since 2023/7/20
 */
@Data
public class EntranceCountSearchBO {

    @ApiModelProperty(value = "岗亭名称")
    @NotBlank(message = "岗亭名称不能为空")
    private String entrance;

    @ApiModelProperty(value = "日期范围")
    private TimeRangeEnum timeRange;

    @ApiModelProperty(value = "起始时间（自定义）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "截止时间（自定义）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private LocalDateTime endTime;

}

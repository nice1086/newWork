package com.ruoyi.biz.domain.BO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author MR.ZHAO
 * @since 2023/7/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeRangeBO {

    /** 起始时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private LocalDateTime startTime;

    /** 终止时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private LocalDateTime endTime;

}

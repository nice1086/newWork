package com.ruoyi.biz.util;

import com.ruoyi.biz.constant.TimeRangeEnum;
import com.ruoyi.biz.domain.BO.TimeRangeBO;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

/**
 * 时间工具类
 * @author Mr.ZHAO
 * @since 2023/7/20
 */
public class TimeUtil {

    /**
     * 获取查询日期范围，不支持时间！
     * @param timeRange 查询日期类型
     * @param startTime 查询日期起始范围（自定义）
     * @param endTime 查询日期终止范围（自定义)
     */
    public static TimeRangeBO getTimeRange(TimeRangeEnum timeRange, LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime start = null;
        LocalDateTime end = null;
        LocalDateTime nowDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        int month = nowDate.getMonthValue();
        LocalDateTime firstDayOfQuarter;
        if (month <= 3) {
            firstDayOfQuarter = LocalDateTime.of(nowDate.getYear(), Month.JANUARY, 1, 0, 0, 0);
        } else if (month <= 6) {
            firstDayOfQuarter = LocalDateTime.of(nowDate.getYear(), Month.APRIL, 1, 0, 0, 0);
        } else if (month <= 9) {
            firstDayOfQuarter = LocalDateTime.of(nowDate.getYear(), Month.JULY, 1, 0, 0, 0);
        } else {
            firstDayOfQuarter = LocalDateTime.of(nowDate.getYear(), Month.OCTOBER, 1, 0, 0, 0);
        }
        if (timeRange != null) {
            switch (timeRange) {
                case DAY:
                    start = nowDate;
                    end = nowDate;
                    break;
                case WEEK:
                    start = nowDate.with(DayOfWeek.MONDAY);
                    end = nowDate.with(DayOfWeek.SUNDAY);
                    break;
                case MONTH:
                    start = nowDate.with(TemporalAdjusters.firstDayOfMonth());
                    end = nowDate.with(TemporalAdjusters.lastDayOfMonth());
                    break;
                case QUARTER:
                    start = firstDayOfQuarter;
                    end = nowDate.plusMonths(3 - nowDate.getMonthValue() % 3).with(TemporalAdjusters.lastDayOfMonth());
                    break;
                case YEAR:
                    start = nowDate.with(TemporalAdjusters.firstDayOfYear());
                    end = nowDate.with(TemporalAdjusters.lastDayOfYear());
                    break;
                case LAST_WEEK:
                    start = nowDate.with(DayOfWeek.MONDAY).minusWeeks(1);
                    end = nowDate.with(DayOfWeek.SUNDAY).minusWeeks(1);
                    break;
                case LAST_MONTH:
                    start = nowDate.with(TemporalAdjusters.firstDayOfMonth()).minusMonths(1);
                    end = nowDate.with(TemporalAdjusters.lastDayOfMonth()).minusMonths(1);
                    break;
                case LAST_QUARTER:
                    start = firstDayOfQuarter.minusMonths(3);
                    end = nowDate.plusMonths(3 - nowDate.getMonthValue() % 3).with(TemporalAdjusters.lastDayOfMonth()).minusMonths(3);
                    break;
                case LAST_YEAR:
                    start = nowDate.with(TemporalAdjusters.firstDayOfYear()).minusYears(1);
                    end = nowDate.with(TemporalAdjusters.lastDayOfYear()).minusYears(1);
                    break;
                case RECENT:
                    start = nowDate.minusDays(2);
                    end = nowDate;
                    break;
                case RECENT_WEEK:
                    start = nowDate.minusDays(6);
                    end = nowDate;
                    break;
                default:
                    start = startTime;
                    end = endTime;
            }
        }
        return new TimeRangeBO(start, end != null ? end.plusDays(1).minusSeconds(1) : null);
    }

}

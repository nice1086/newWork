package com.ruoyi.biz.constant;

/**
 * 时间枚举
 * @author MR.ZHAO
 * @since 2023/7/20
 */
public enum TimeRangeEnum {

    /** 时间枚举 **/
    DAY(0, "今天"),
    WEEK(1, "本周"),
    MONTH(2, "本月"),
    QUARTER(3, "本季度"),
    YEAR(4, "本年"),
    LAST_WEEK(5, "上周"),
    LAST_MONTH(6, "上月"),
    LAST_QUARTER(7, "上季度"),
    LAST_YEAR(8, "去年"),
    RECENT(9, "近三天"),
    RECENT_WEEK(10, "近一周"),
    SELF_TIME(11, "自定义时间");


    private Integer type;
    private String label;

    TimeRangeEnum(Integer type, String label) {
        this.type = type;
        this.label = label;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

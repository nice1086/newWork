package com.ruoyi.biz.domain.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 柱状图数据
 * @author MR.ZHAO
 * @since 2023/7/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnarDataVO {

    /** 名称数据（X轴） **/
    @ApiModelProperty(value = "名称数据（X轴）")
    private List<Object> nameData;

    /** 入厂数量（Y轴1） **/
    @ApiModelProperty(value = "入厂数量（Y轴1）")
    private List<Long> inData;

    /** 出厂数量（Y轴2） **/
    @ApiModelProperty(value = "出厂数量（Y轴2）")
    private List<Long> outData;

    /** 总数量 **/
    @ApiModelProperty(value = "总数量")
    private List<Long> sumData;

}

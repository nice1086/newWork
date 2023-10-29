package com.ruoyi.biz.domain.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MR.ZHAO
 * @since 2023/7/20
 */
@Data
public class CompanyDataAnalysisVO {

    @ApiModelProperty(value = "企业编码")
    private Long companyId;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "进场量")
    private Long inCount;

    @ApiModelProperty(value = "出场量")
    private Long outCount;

    @ApiModelProperty(value = "总量")
    private Long sumCount;

    @ApiModelProperty(value = "占比")
    private Double scale;

}

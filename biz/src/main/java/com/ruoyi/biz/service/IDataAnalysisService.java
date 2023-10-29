package com.ruoyi.biz.service;

import com.ruoyi.biz.domain.BO.EntranceCountSearchBO;
import com.ruoyi.biz.domain.VO.ColumnarDataVO;
import com.ruoyi.biz.domain.VO.CompanyDataAnalysisVO;
import com.ruoyi.biz.domain.VO.ResidentCompanyVO;

import java.util.List;

/**
 * 数据统计服务类接口
 * @author MR.ZHAO
 * @since 2023/7/20
 */
public interface IDataAnalysisService {

    /**
     * 获取岗亭名称
     * @return 岗亭名称列表
     */
    List<String> getEntranceName();

    /**
     * 获取岗亭车辆实时统计
     * @return 岗亭车辆实时统计结果
     */
    ColumnarDataVO getEntranceCountName(EntranceCountSearchBO searchBO);


    /**
     * 获取岗亭车辆统计
     * @param searchBO 获取岗亭车辆统计查询条件
     * @return 获取岗亭车辆统计结果
     */
    ColumnarDataVO getEntranceCount(EntranceCountSearchBO searchBO);


    /**
     * 获取企业车辆统计
     * @return 企业车辆统计结果
     */
    List<CompanyDataAnalysisVO> getCompanyCount();


    /**
     * 统计车辆出入数量
     * @param searchBO 统计范围
     * @param all 是否全部
     * @return 车辆出入数量
     */
    Long getVehicleCount(EntranceCountSearchBO searchBO, boolean all);

}

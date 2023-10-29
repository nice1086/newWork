package com.ruoyi.biz.mapper;

import com.ruoyi.biz.domain.BO.EntranceCountSearchBO;
import com.ruoyi.biz.domain.VO.ColumnarDataVO;
import com.ruoyi.biz.domain.VO.CompanyDataAnalysisVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author MR.ZHAO
 * @since 2023/7/20
 */
public interface DataAnalysisMapper {

    /**
     * 获取岗亭名称
     * @return 岗亭名称列表
     */
    List<String> selectEntranceName();

    /**
     * 获取岗亭车辆统计（名称）
     * @param searchBO 获取岗亭车辆统计查询条件
     * @return 获取岗亭车辆统计结果（名称）
     */
    List<Map<String, Object>> selectEntranceCountName(EntranceCountSearchBO searchBO);

    /**
     * 获取岗亭车辆统计（日）
     * @param searchBO 获取岗亭车辆统计查询条件
     * @return 获取岗亭车辆统计结果（日）
     */
    List<Map<String, Object>> selectEntranceCountDay(EntranceCountSearchBO searchBO);

    /**
     * 获取岗亭车辆统计（小时）
     * @param searchBO 获取岗亭车辆统计查询条件
     * @return 获取岗亭车辆统计结果（小时）
     */
    List<Map<String, Object>> selectEntranceCountHour(EntranceCountSearchBO searchBO);

    /**
     * 获取驻区企业下的车辆统计
     * @param plateNos 驻区企业的车牌
     * @return 驻区企业下的车辆统计
     */
    Map<String, Object> selectCompanyCount(@Param("plateNos") List<String> plateNos);

    /**
     * 统计车辆出入数量
     * @param searchBO 统计范围
     * @param all 是否全部
     * @return 车辆出入数量
     */
    Long getVehicleCount(@Param("search") EntranceCountSearchBO searchBO, @Param("all") boolean all);

}

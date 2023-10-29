package com.ruoyi.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.biz.domain.PO.Company;
import com.ruoyi.biz.domain.VO.ResidentCompanyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 驻区企业Mapper接口
 * 
 * @author MR.ZHAO
 * @date 2023-07-13
 */
public interface ResidentCompanyMapper extends BaseMapper<Company>
{
    /**
     * 查询驻区企业
     * 
     * @param companyId 驻区企业主键
     * @return 驻区企业
     */
    ResidentCompanyVO selectCompanyByCompanyId(Long companyId);

    /**
     * 查询驻区企业列表
     * 
     * @param company 驻区企业
     * @return 驻区企业集合
     */
    List<ResidentCompanyVO> selectCompanyList(Company company);

    /**
     * 新增驻区企业
     * 
     * @param company 驻区企业
     * @return 结果
     */
    int insertCompany(Company company);

    /**
     * 修改驻区企业
     * 
     * @param company 驻区企业
     * @return 结果
     */
    int updateCompany(Company company);

    /**
     * 删除驻区企业
     * 
     * @param companyId 驻区企业主键
     * @return 结果
     */
    int deleteCompanyByCompanyId(Long companyId);

    /**
     * 批量删除驻区企业
     * 
     * @param companyIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteCompanyByCompanyIds(Long[] companyIds);

    /**
     * 获取驻区企业创建的运输企业ID列表
     * @param companyId 驻区企业ID
     * @return 运输企业ID列表
     */
    List<Long> selectTransport(@Param("companyId") Long companyId);

    /**
     * 获取驻区企业即其负责的运输企业的所有车辆数
     * @param companyIds 驻区企业和其负责的运输企业ID列表
     * @return 驻区企业下的运输企业的所有车辆数
     */
    int selectVehicleCount(@Param("companyIds") List<Long> companyIds);

    /**
     * 获取驻区企业即其负责的运输企业的所有车牌
     * @param companyIds 驻区企业和其负责的运输企业ID列表
     * @return 驻区企业下的运输企业的所有车牌
     */
    List<String> selectCarNo(@Param("companyIds") List<Long> companyIds);

    /**
     * 检查唯一性
     * @param company 车辆信息
     * @return 唯一性检查结果
     */
    int checkUnique(Company company);

    /**
     * 获取企业子账户数量
     * @param companyId 企业ID
     * @return 企业子账户数量
     */
    int countSubAccount(@Param("companyId") Long companyId);

}

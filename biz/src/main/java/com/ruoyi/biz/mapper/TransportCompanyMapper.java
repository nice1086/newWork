package com.ruoyi.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.biz.domain.PO.Company;
import com.ruoyi.biz.domain.VO.TransportCompanyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 承运企业Mapper接口
 * 
 * @author MR.ZHAO
 * @date 2023-07-13
 */
public interface TransportCompanyMapper extends BaseMapper<Company>
{
    /**
     * 查询承运企业
     * 
     * @param companyId 承运企业主键
     * @return 承运企业
     */
    TransportCompanyVO selectCompanyByCompanyId(Long companyId);

    /**
     * 查询承运企业列表
     * 
     * @param company 承运企业
     * @return 承运企业集合
     */
    List<TransportCompanyVO> selectCompanyList(Company company);

    /**
     * 新增承运企业
     * 
     * @param company 承运企业
     * @return 结果
     */
    int insertCompany(Company company);

    /**
     * 修改承运企业
     * 
     * @param company 承运企业
     * @return 结果
     */
    int updateCompany(Company company);

    /**
     * 删除承运企业
     * 
     * @param companyId 承运企业主键
     * @return 结果
     */
    int deleteCompanyByCompanyId(Long companyId);

    /**
     * 批量删除承运企业
     * 
     * @param companyIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteCompanyByCompanyIds(Long[] companyIds);

    /**
     * 获取承运企业下的车辆数
     * @param companyId 承运企业ID
     * @return 承运企业下的车辆数
     */
    int selectVehicleCount(@Param("companyId") Long companyId);

    /**
     * 检查唯一性
     * @param company 车辆信息
     * @return 唯一性检查结果
     */
    int checkUnique(Company company);

    /**
     * 根据企业名称查询企业ID
     * @param companyId
     * @return
     */
    Long queryCompanyIdByCompanyName(String companyId);


    /**
     * 根据机构号查询机构，认领机构
     * @param orgCode
     * @return
     */
    Company queryCompanyByOrgCode(@Param("orgCode") String orgCode);
}

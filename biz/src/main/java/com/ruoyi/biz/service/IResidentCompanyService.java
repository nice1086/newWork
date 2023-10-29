package com.ruoyi.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.biz.domain.BO.ResidentCompanySaveBO;
import com.ruoyi.biz.domain.PO.Company;
import com.ruoyi.biz.domain.VO.ResidentCompanyVO;

import java.util.List;

/**
 * 驻区企业Service接口
 * 
 * @author MR.ZHAO
 * @date 2023-07-13
 */
public interface IResidentCompanyService extends IService<Company>
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
     * @param companySaveBO 驻区企业
     * @return 结果
     */
    int insertCompany(ResidentCompanySaveBO companySaveBO);

    /**
     * 修改驻区企业
     * 
     * @param companySaveBO 企业
     * @return 结果
     */
    int updateCompany(ResidentCompanySaveBO companySaveBO);

    /**
     * 批量删除驻区企业
     * 
     * @param companyIds 需要删除的驻区企业主键集合
     * @return 结果
     */
    int deleteCompanyByCompanyIds(Long[] companyIds);

    /**
     * 删除驻区企业信息
     * 
     * @param companyId 驻区企业主键
     * @return 结果
     */
     int deleteCompanyByCompanyId(Long companyId);


    /**
     * 导入驻区企业信息
     *
     * @param companySaveBOList 驻区企业信息列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    String importData(List<ResidentCompanySaveBO> companySaveBOList, Boolean isUpdateSupport);


    /**
     * 获取驻区企业即其负责的运输企业的所有车牌
     * @param companyIds 驻区企业和其负责的运输企业ID列表
     * @return 驻区企业下的运输企业的所有车牌
     */
    List<String> selectCarNo(List<Long> companyIds);

    /**
     * 获取驻区企业创建的运输企业ID列表
     * @param companyId 驻区企业ID
     * @return 运输企业ID列表
     */
    List<Long> selectTransport(Long companyId);

    /**
     * 获取驻区企业即其负责的运输企业的所有车辆数
     * @param companyIds 驻区企业和其负责的运输企业ID列表
     * @return 驻区企业下的运输企业的所有车辆数
     */
    int selectVehicleCount(List<Long> companyIds);

    /**
     * 获取企业子账户数量
     * @param companyId 企业ID
     * @return 企业子账户数量
     */
    int countSubAccount(Long companyId);

}

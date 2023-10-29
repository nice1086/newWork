package com.ruoyi.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.biz.domain.BO.TransportCompanySaveBO;
import com.ruoyi.biz.domain.PO.Company;
import com.ruoyi.biz.domain.VO.TransportCompanyVO;

import java.util.List;

/**
 * 承运企业Service接口
 * 
 * @author MR.ZHAO
 * @date 2023-07-13
 */
public interface ITransportCompanyService extends IService<Company>
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
     * @param companySaveBO 承运企业
     * @return 结果
     */
    int insertCompany(TransportCompanySaveBO companySaveBO);

    /**
     * 修改承运企业
     * 
     * @param company 企业
     * @return 结果
     */
    int updateCompany(TransportCompanySaveBO company);

    /**
     * 批量删除承运企业
     * 
     * @param companyIds 需要删除的承运企业主键集合
     * @return 结果
     */
    int deleteCompanyByCompanyIds(Long[] companyIds);

    /**
     * 删除承运企业信息
     * 
     * @param companyId 承运企业主键
     * @return 结果
     */
     int deleteCompanyByCompanyId(Long companyId);

    /**
     * 导入承运企业信息
     *
     * @param companySaveBOList 承运企业信息列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    String importData(List<TransportCompanySaveBO> companySaveBOList, Boolean isUpdateSupport);

    TransportCompanyVO queryTCompanyByOrgCode(String orgCode);
}

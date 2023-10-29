package com.ruoyi.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.biz.domain.BO.TransportCompanySaveBO;
import com.ruoyi.biz.domain.PO.Company;
import com.ruoyi.biz.domain.VO.TransportCompanyVO;
import com.ruoyi.biz.mapper.TransportCompanyMapper;
import com.ruoyi.biz.service.ITransportCompanyService;
import com.ruoyi.biz.util.ValidatorUtils;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.vo.CompanyUserVO;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 承运企业Service业务层处理
 * 
 * @author MR.ZHAO
 * @date 2023-07-13
 */
@Service
public class TransportCompanyServiceImpl extends ServiceImpl<TransportCompanyMapper, Company> implements ITransportCompanyService
{
    @Autowired
    private TransportCompanyMapper transportCompanyMapper;

    @Autowired
    private ISysUserService sysUserService;

    private final Long TRAN_DEP_ID = 201L;

    /**
     * 查询承运企业
     * 
     * @param companyId 承运企业主键
     * @return 承运企业
     */
    @Override
    public TransportCompanyVO selectCompanyByCompanyId(Long companyId)
    {
        TransportCompanyVO transportCompanyVO = transportCompanyMapper.selectCompanyByCompanyId(companyId);
        transportCompanyVO.setVehicleCount(transportCompanyMapper.selectVehicleCount(companyId));
        return transportCompanyVO;
    }

    /**
     * 查询承运企业列表
     * 
     * @param company 承运企业
     * @return 承运企业
     */
    @Override
    public List<TransportCompanyVO> selectCompanyList(Company company)
    {
        SysUser loginUser = SecurityUtils.getLoginUser().getUser();
        boolean adminRole = false;
        for (SysRole role : loginUser.getRoles()) {
            if ("admin".equals(role.getRoleKey()) || "manage".equals(role.getRoleKey())) {
                adminRole = true;
                break;
            }
        }
        if (!adminRole) {
            company.setCreateCompanyId(loginUser.getCompanyId() != null ? loginUser.getCompanyId() : 1L);
        }
        PageUtils.startPage();
        List<TransportCompanyVO> transportCompanyVOList = transportCompanyMapper.selectCompanyList(company);
        transportCompanyVOList.forEach(v -> v.setVehicleCount(transportCompanyMapper.selectVehicleCount(v.getCompanyId())));
        return transportCompanyVOList;
    }

    /**
     * 新增承运企业
     * 
     * @param companySaveBO 承运企业
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertCompany(TransportCompanySaveBO companySaveBO)
    {
        Company company = BeanUtil.toBean(companySaveBO, Company.class);
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        int res;
        synchronized (this) {
            if (transportCompanyMapper.checkUnique(company) > 0) {
                throw new ServiceException("公司名称/联系电话/公司编码/统一社会信用代码 不允许重复！");
            }
            // 设置注册类型为承运企业,设置创建用户信息，责任企业id
            company.setRegisteType(2);
            company.setCreateCompanyId(sysUser.getCompanyId() != null ? sysUser.getCompanyId() : 1);
            company.setCreateBy(sysUser.getUserName());
            company.setCreateTime(DateUtils.getNowDate());
            if (company.getStatus() == null) {
                company.setStatus("0");
            }
            res = transportCompanyMapper.insertCompany(company);
        }
        // 保存用户
        CompanyUserVO companyUserVO = BeanUtil.copyProperties(company, CompanyUserVO.class);
        companyUserVO.setDeptId(TRAN_DEP_ID);
        companyUserVO.setPassword(companySaveBO.getPassword());
        sysUserService.insertCompanyUser(companyUserVO);
        return res;
    }

    /**
     * 修改承运企业
     * 
     * @param companySaveBO 承运企业
     * @return 结果
     */
    @Override
    public int updateCompany(TransportCompanySaveBO companySaveBO)
    {
        if (companySaveBO.getCompanyId() == null) {
            throw new ServiceException("承运企业ID不能为空！");
        }
        Company company = BeanUtil.toBean(companySaveBO, Company.class);
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        int res;
        synchronized (this) {
            if (transportCompanyMapper.checkUnique(company) > 0) {
                throw new ServiceException("公司名称/联系电话/公司编码/统一社会信用代码 不允许重复！");
            }
            // 设置更新人，更新时间
            company.setUpdateTime(DateUtils.getNowDate());
            company.setUpdateBy(SecurityUtils.getUsername());
            company.setCreateCompanyId(sysUser.getCompanyId() != null ? sysUser.getCompanyId() : 0);
            res = transportCompanyMapper.updateCompany(company);
        }
        return res;
    }

    /**
     * 批量删除承运企业
     * 
     * @param companyIds 需要删除的承运企业主键
     * @return 结果
     */
    @Override
    public int deleteCompanyByCompanyIds(Long[] companyIds)
    {
        return transportCompanyMapper.deleteCompanyByCompanyIds(companyIds);
    }

    /**
     * 删除承运企业信息
     * 
     * @param companyId 承运企业主键
     * @return 结果
     */
    @Override
    public int deleteCompanyByCompanyId(Long companyId)
    {
        return transportCompanyMapper.deleteCompanyByCompanyId(companyId);
    }

    /**
     * 导入承运企业信息
     *
     * @param companySaveBOList 承运企业信息列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importData(List<TransportCompanySaveBO> companySaveBOList, Boolean isUpdateSupport)
    {
        int successNum = 0;
        int failureNum = 0;
        int repeatNum = 0;
        List<TransportCompanyVO> existList = selectCompanyList(new Company());
        String errorMsg = null;
        boolean firstErr = false;
        for (TransportCompanySaveBO importData : companySaveBOList)
        {
            try {
                ValidatorUtils.validateFast(importData);
                Long companyId = null;
                for (TransportCompanyVO entry : existList) {
                    if (entry.getCompanyName().equals(importData.getCompanyName())) {
                        companyId = entry.getCompanyId();
                        repeatNum++;
                        break;
                    }
                }
                if (companyId == null) {
                    insertCompany(importData);
                    successNum++;
                } else if (isUpdateSupport) {
                    importData.setCompanyId(companyId);
                    updateCompany(importData);
                    successNum++;
                }
            } catch (Exception e) {
                if (!firstErr) {
                    errorMsg = "导入企业：" + importData.getCompanyName() + "，出现异常：" + e.getMessage();
                    firstErr = true;
                }
                failureNum++;
            }
        }
        StringBuilder msg = new StringBuilder();
        msg.append("成功导入：").append(successNum).append("条数据，失败导入：").append(failureNum)
                .append("条数据，包含已存在承运企业").append(repeatNum).append("条。");
        if (errorMsg != null) {
            msg.append("最近一次失败原因：").append(errorMsg);
        }
        return msg.toString();
    }

    @Override
    public TransportCompanyVO queryTCompanyByOrgCode(String orgCode) {
        Company company = transportCompanyMapper.queryCompanyByOrgCode(orgCode);
        if(ObjectUtil.isEmpty(company)){
            return null;
        }
        TransportCompanyVO transportCompanyVO = new TransportCompanyVO();
        BeanUtils.copyProperties(company, transportCompanyVO);
        return transportCompanyVO;
    }

}

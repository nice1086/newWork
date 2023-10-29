package com.ruoyi.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.biz.util.ValidatorUtils;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.vo.CompanyUserVO;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.biz.domain.BO.ResidentCompanySaveBO;
import com.ruoyi.biz.domain.PO.Company;
import com.ruoyi.biz.domain.VO.ResidentCompanyVO;
import com.ruoyi.biz.mapper.ResidentCompanyMapper;
import com.ruoyi.biz.service.IResidentCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 驻区企业Service业务层处理
 * 
 * @author MR.ZHAO
 * @date 2023-07-13
 */
@Service
public class ResidentCompanyServiceImpl extends ServiceImpl<ResidentCompanyMapper, Company> implements IResidentCompanyService
{
    @Autowired
    private ResidentCompanyMapper residentCompanyMapper;

    @Autowired
    private ISysUserService sysUserService;

    private final Long RES_DEP_ID = 101L;

    /**
     * 查询驻区企业
     * 
     * @param companyId 驻区企业主键
     * @return 驻区企业
     */
    @Override
    public ResidentCompanyVO selectCompanyByCompanyId(Long companyId)
    {
        ResidentCompanyVO residentCompanyVO = residentCompanyMapper.selectCompanyByCompanyId(companyId);
        List<Long> transportIds = selectTransport(companyId);
        transportIds.add(companyId);
        residentCompanyVO.setTransportCount(transportIds.size());
        if (transportIds.size() != 0) {
            residentCompanyVO.setVehicleCount(selectVehicleCount(transportIds));
        } else {
            residentCompanyVO.setVehicleCount(0);
        }
        return residentCompanyVO;
    }

    /**
     * 查询驻区企业列表
     * 
     * @param company 驻区企业
     * @return 驻区企业
     */
    @Override
    public List<ResidentCompanyVO> selectCompanyList(Company company)
    {
        List<ResidentCompanyVO> residentCompanyVOList = residentCompanyMapper.selectCompanyList(company);
        residentCompanyVOList.forEach(v -> {
            List<Long> transportIds = selectTransport(v.getCompanyId());
            transportIds.add(v.getCompanyId());
            v.setTransportCount(transportIds.size());
            if (transportIds.size() != 0) {
                v.setVehicleCount(selectVehicleCount(transportIds));
            } else {
                v.setVehicleCount(0);
            }
        });
        return residentCompanyVOList;
    }

    /**
     * 新增驻区企业
     * 
     * @param companySaveBO 驻区企业
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertCompany(ResidentCompanySaveBO companySaveBO)
    {
        Company company = BeanUtil.toBean(companySaveBO, Company.class);
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        int res;
        synchronized (this) {
            if (residentCompanyMapper.checkUnique(company) > 0) {
                throw new ServiceException("公司名称/联系电话/公司编码/统一社会信用代码 不允许重复！");
            }
            // 设置注册类型为驻区企业，设置创建人，创建时间
            company.setRegisteType(1);
            company.setCreateBy(sysUser.getUserName());
            company.setCreateTime(DateUtils.getNowDate());
            if (company.getStatus() == null) {
                company.setStatus("0");
            }
            res = residentCompanyMapper.insertCompany(company);
        }
        // 保存用户
        CompanyUserVO companyUserVO = BeanUtil.copyProperties(company, CompanyUserVO.class);
        companyUserVO.setDeptId(RES_DEP_ID);
        companyUserVO.setPassword(companySaveBO.getPassword());
        sysUserService.insertCompanyUser(companyUserVO);
        return res;
    }

    /**
     * 修改驻区企业
     * 
     * @param companySaveBO 驻区企业
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCompany(ResidentCompanySaveBO companySaveBO)
    {
        if (companySaveBO.getCompanyId() == null) {
            throw new ServiceException("驻区企业ID不能为空！");
        }
        // 禁止修改青岛开发区石化封闭区
        if (companySaveBO.getCompanyId() == 1L) {
            throw new ServiceException("为保证系统正常，青岛开发区石化封闭区禁止修改！");
        }
        // 如果修改企业类型为小型企业，则需要检查是否存在子账号
        if (companySaveBO.getCompanySize() != null && companySaveBO.getCompanySize() == 1) {
            int subAccount = countSubAccount(companySaveBO.getCompanyId());
            if (subAccount != 0) {
                throw new ServiceException("该企业下存在子账号，请删除子账号后再修改企业类型为小型企业！");
            }
        }
        Company company = BeanUtil.toBean(companySaveBO, Company.class);
        int res;
        synchronized (this) {
            if (residentCompanyMapper.checkUnique(company) > 0) {
                throw new ServiceException("公司名称/联系电话/公司编码/统一社会信用代码 不允许重复！");
            }
            // 设置更新人，更新时间
            company.setUpdateTime(DateUtils.getNowDate());
            company.setUpdateBy(SecurityUtils.getUsername());
            res = residentCompanyMapper.updateCompany(company);
        }
        try {
            CompanyUserVO companyUserVO = new CompanyUserVO();
            companyUserVO.setCompanyId(companySaveBO.getCompanyId());
            companyUserVO.setCompanySize(companySaveBO.getCompanySize());
            companyUserVO.setCompanyType(companySaveBO.getCompanyType());
            sysUserService.updateCompanyUser(companyUserVO);
        } catch (Exception e) {
            throw new ServiceException("修改用户信息异常，请联系管理员");
        }
        return res;
    }

    /**
     * 批量删除驻区企业
     * 
     * @param companyIds 需要删除的驻区企业主键
     * @return 结果
     */
    @Override
    public int deleteCompanyByCompanyIds(Long[] companyIds)
    {
        return residentCompanyMapper.deleteCompanyByCompanyIds(companyIds);
    }

    /**
     * 删除驻区企业信息
     * 
     * @param companyId 驻区企业主键
     * @return 结果
     */
    @Override
    public int deleteCompanyByCompanyId(Long companyId)
    {
        return residentCompanyMapper.deleteCompanyByCompanyId(companyId);
    }

    /**
     * 导入驻区企业信息
     *
     * @param companySaveBOList 驻区企业信息列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importData(List<ResidentCompanySaveBO> companySaveBOList, Boolean isUpdateSupport)
    {
        int successNum = 0;
        int failureNum = 0;
        int repeatNum = 0;
        String errorMsg = null;
        boolean firstErr = false;
        List<ResidentCompanyVO> existList = selectCompanyList(null);
        for (ResidentCompanySaveBO importData : companySaveBOList)
        {
            try {
                ValidatorUtils.validateFast(importData);
                Long companyId = null;
                for (ResidentCompanyVO entry : existList) {
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
                    errorMsg = "导入" + importData.getCompanyName() + "出现异常：" + e.getMessage();
                    firstErr = true;
                }
                failureNum++;
            }
        }
        StringBuilder msg = new StringBuilder();
        msg.append("成功导入：").append(successNum).append("条数据，失败导入：").append(failureNum)
                .append("条数据，包含已存在驻区企业").append(repeatNum).append("条。");
        if (errorMsg != null) {
            msg.append("最近一次失败原因：").append(errorMsg);
        }
        return msg.toString();
    }

    /**
     * 获取驻区企业即其负责的运输企业的所有车牌
     *
     * @param companyIds 驻区企业和其负责的运输企业ID列表
     * @return 驻区企业下的运输企业的所有车牌
     */
    @Override
    public List<String> selectCarNo(List<Long> companyIds) {
        if (companyIds == null || companyIds.size() == 0) {
            return new ArrayList<>();
        }
        return residentCompanyMapper.selectCarNo(companyIds);
    }

    /**
     * 获取驻区企业创建的运输企业ID列表
     *
     * @param companyId 驻区企业ID
     * @return 运输企业ID列表
     */
    @Override
    public List<Long> selectTransport(Long companyId) {
        if (companyId == null) {
            return new ArrayList<>();
        }
        return residentCompanyMapper.selectTransport(companyId);
    }

    /**
     * 获取驻区企业即其负责的运输企业的所有车辆数
     *
     * @param companyIds 驻区企业和其负责的运输企业ID列表
     * @return 驻区企业下的运输企业的所有车辆数
     */
    @Override
    public int selectVehicleCount(List<Long> companyIds) {
        if (companyIds == null || companyIds.size() == 0) {
            return 0;
        }
        return residentCompanyMapper.selectVehicleCount(companyIds);
    }

    /**
     * 获取企业子账户数量
     *
     * @param companyId 企业ID
     * @return 企业子账户数量
     */
    @Override
    public int countSubAccount(Long companyId) {
        return residentCompanyMapper.countSubAccount(companyId);
    }

}

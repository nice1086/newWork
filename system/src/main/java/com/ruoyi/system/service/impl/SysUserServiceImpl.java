package com.ruoyi.system.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.domain.SysUserPost;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.domain.vo.CompanyUserVO;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysUserServiceImpl implements ISysUserService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    protected Validator validator;

    @Autowired
    private ISysRoleService roleService;

    /**
     * 根据条件分页查询用户列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(userAlias = "u")
    public List<SysUser> selectUserList(SysUser user)
    {

        //用户验证
        SysUser user1 = SecurityUtils.getLoginUser().getUser();
        SysUser sysUser = userMapper.selectUserById(user1.getUserId());
        if(ObjectUtil.isNotEmpty(sysUser)){
            if(sysUser.getUserType()!=null){
                user.setUserTypeParent(sysUser.getUserType().trim());
            }
            if(sysUser.getCompanyId()!=null){
                user.setCompanyId(sysUser.getCompanyId());
            }
            
        }
        PageUtils.startPage();
        return userMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user)
    {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user)
    {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName)
    {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     * 
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName)
    {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * 校验用户名称是否唯一
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean checkUserNameUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkUserNameUnique(user.getUserName());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public boolean checkPhoneUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public boolean checkEmailUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     * 
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    /**
     * 校验用户是否有数据权限
     * 
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users))
            {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    /**
     * 新增保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user)
    {

        //用户验证
        SysUser user1 = SecurityUtils.getLoginUser().getUser();
        SysUser sysUser = userMapper.selectUserById(user1.getUserId());
        if(ObjectUtil.isNotEmpty(sysUser)&& sysUser.getCompanyId()!=null){
            if(user.getCompanyId()==null){
                user.setCompanyId(sysUser.getCompanyId());
            }
        }
        // 新增用户信息
        user.setCreateBy(sysUser.getUserId()+"");
        int rows = userMapper.insertUser(user);

        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 注册用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user)
    {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 修改保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user)
    {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        return userMapper.updateUser(user);
    }

    /**
     * 用户授权角色
     * 
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional
    public void insertUserAuth(Long userId, Long[] roleIds)
    {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户状态
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     * 
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar)
    {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     * 
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password)
    {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 新增用户角色信息
     * 
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user)
    {
        this.insertUserRole(user.getUserId(), user.getRoleIds());
    }


    /**
     * 新增用户角色信息
     * 
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds)
    {
        if (StringUtils.isNotEmpty(roleIds))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>(roleIds.length);
            for (Long roleId : roleIds)
            {
                if(roleId!=1){
                    SysUserRole ur = new SysUserRole();
                    ur.setUserId(userId);
                    ur.setRoleId(roleId);
                    list.add(ur);
                }
            }
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId)
    {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);

        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds)
    {
        for (Long userId : userIds)
        {
            checkUserAllowed(new SysUser(userId));
            checkUserDataScope(userId);
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);

        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     * 
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList)
        {
            try
            {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, user);
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    userMapper.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, user);
                    checkUserAllowed(u);
                    checkUserDataScope(u.getUserId());
                    user.setUserId(u.getUserId());
                    user.setUpdateBy(operName);
                    userMapper.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }



    /**
     * 根据条件分页查询子用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @DataScope(userAlias = "u")
    @Override
    public List<SysUser> selectUserSonList(SysUser user) {
            //用户验证
            SysUser user1 = SecurityUtils.getLoginUser().getUser();
            SysUser sysUser = userMapper.selectUserById(user1.getUserId());
            if(ObjectUtil.isNotEmpty(sysUser)){
                if(sysUser.getCompanyId()!=null){
                    user.setCompanyId(sysUser.getCompanyId());
                }
            }
        // 角色集合
        List<SysRole> sysRoles = roleService.selectAllAuthUserList(user1.getUserId());
        if(sysRoles.size()>0){
            List<SysRole> collect = sysRoles.stream().filter(x ->
                "large_company".equals(x.getRoleKey().trim())
                ).collect(Collectors.toList());
            if(collect.size()>0){
                user.setCreateBy(user1.getUserId().toString());
            }

        }
        sysRoles.forEach(item->{
            if(CharSequenceUtil.equals(item.getRoleKey().trim(), "manage")){
                user.setCompanyId(null);
            }
        });
            PageUtils.startPage();

            return userMapper.selectUserSonList(user);
    }
    /**
     * 导入子用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String sonImportUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
            if (StringUtils.isNull(userList) || userList.size() == 0)
            {
                throw new ServiceException("导入用户数据不能为空！");
            }
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            String password = configService.selectConfigByKey("sys.user.initPassword");
            for (SysUser user : userList)
            {
                try
                {
                    // 验证是否存在这个用户
                    SysUser u = userMapper.selectUserByUserName(user.getUserName());
                    if (StringUtils.isNull(u))
                    {
                        BeanValidators.validateWithException(validator, user);
                        user.setPassword(SecurityUtils.encryptPassword(password));
                        user.setCreateBy(operName);
                        userMapper.insertUser(user);
                        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
                        // 角色集合
                        List<SysRole> sysRoles = roleService.selectRolesByUserId(sysUser.getUserId());
                        if(sysRoles.size()>0){
                            //存储用户与角色关联表数据
                            List<SysUserRole> roleList = new ArrayList<>();
                            sysRoles.stream().filter(x->
                                    !"admin".equals(x.getRoleKey()) && !"common".equals(x.getRoleKey()) && !"large_company".equals(x.getRoleKey())
                            ).forEach(x->{
                                SysUserRole sysUserRole = new SysUserRole();
                                sysUserRole.setUserId(sysUser.getUserId());
                                sysUserRole.setRoleId(x.getRoleId());
                                roleList.add(sysUserRole);
                            });
                            userRoleMapper.batchUserRole(roleList);
                        }
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                    }
                    else if (isUpdateSupport)
                    {
                        BeanValidators.validateWithException(validator, user);
                        checkUserAllowed(u);
                        checkUserDataScope(u.getUserId());
                        user.setUserId(u.getUserId());
                        user.setUpdateBy(operName);
                        userMapper.updateUser(user);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                    } 
                    else
                    {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                    }
                }
                catch (Exception e)
                {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                    failureMsg.append(msg + e.getMessage());
                    log.error(msg, e);
                }
            }
        if (failureNum > 0)
            {
                failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
                throw new ServiceException(failureMsg.toString());
            }
            else
            {
                successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
            }
            return successMsg.toString();
    }

    /**
     * 新增保存子用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSonUser(SysUser user) {
            //用户验证
            SysUser user1 = SecurityUtils.getLoginUser().getUser();
//            SysUser sysUser = userMapper.selectUserById(user1.getUserId());
            user.setCompanyId(user1.getCompanyId());
            user.setCreateTime(DateUtils.getNowDate());
            user.setCreateBy(user1.getUserId()+"");
            user.setUpdateBy(user1.getUserName());
            user.setUpdateTime(DateUtils.getNowDate());
            user.setDeptId(101L);
            user.setUserType("02");
            // 新增用户信息
            int rows = userMapper.insertUser(user);
            // 角色集合
            List<SysRole> sysRoles = roleService.selectAllAuthUserList(user1.getUserId());
            if(sysRoles.size()>0){
                //存储用户与角色关联表数据
                List<SysUserRole> roleList = new ArrayList<>();
                List<SysRole> collect = sysRoles.stream().filter(x ->
                        !"admin".equals(x.getRoleKey().trim()) && !"common".equals(x.getRoleKey().trim()) && !"large_company".equals(x.getRoleKey().trim()) && !"manage".equals(x.getRoleKey().trim())
                ).collect(Collectors.toList());
                for (SysRole sysRole : collect) {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(user.getUserId());
                    sysUserRole.setRoleId(sysRole.getRoleId());
                    roleList.add(sysUserRole);
                }
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(user.getUserId());
                sysUserRole.setRoleId(Long.valueOf("105"));
                roleList.add(sysUserRole);
                userRoleMapper.batchUserRole(roleList);
            }

//            // 新增用户与角色管理
//            insertUserRole(user);
            return rows;
    }


    /**
     * 根据用户ID查询其关联企业状态
     *
     * @param userName 用户名
     * @return 企业状态
     */
    @Override
    public String selectCompanyStatus(String userName) {
        return userMapper.selectCompanyStatus(userName);
    }
}

package com.ruoyi.framework.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.framework.web.service.UserOpenIdDetailsService;
import com.ruoyi.system.mapper.SysUserMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 根基openid获取用户信息
 */
@Service
public class UserOpenIdDetailServiceImpl implements UserOpenIdDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserOpenIdDetailServiceImpl.class);
	
	@Autowired
    private SysUserMapper userMapper;
	
	@Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByOpenId(String openId) throws UsernameNotFoundException {
        //根据用户名在数据库中查询用户信息
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openId);
        SysUser user = userMapper.selectOne(queryWrapper);
        
        if (StringUtils.isNull(user))
        {
            log.info("登录用户openId：{} 不存在.", openId);
            throw new ServiceException("登录用户openId：" + openId + " 不存在");
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            log.info("登录用户openId：{} 已被删除.", openId);
            throw new ServiceException("对不起，您的账号openId：" + openId + " 已被删除");
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            log.info("登录用户openId：{} 已被停用.", openId);
            throw new ServiceException("对不起，您的账号openId：" + openId + " 已停用");
        }

        return createLoginUser(user);
    }
    
    public UserDetails createLoginUser(SysUser user)
    {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
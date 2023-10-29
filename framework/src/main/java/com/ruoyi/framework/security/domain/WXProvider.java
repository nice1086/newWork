package com.ruoyi.framework.security.domain;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.framework.web.service.UserOpenIdDetailsService;

/**
 * 微信调用的provider
 */
@Data
public class WXProvider implements AuthenticationProvider {

    private UserOpenIdDetailsService userOpenIdDetailsService;

    /**
     * 取到authentication中的openId，根据openId查询信息，能查到信息表示登陆成功
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginUser loginUser = (LoginUser) userOpenIdDetailsService.loadUserByOpenId(authentication.getPrincipal().toString());
        if(loginUser == null){
            throw new RuntimeException("登陆失败");
        }
        WXAuthenticationToken wxAuthenticationToken = new WXAuthenticationToken(loginUser);
        return wxAuthenticationToken;
    }

    /**
     * 配置当前Provider对应的wxAuthenticationToken
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (WXAuthenticationToken.class.isAssignableFrom(authentication));
    }
}

package com.ruoyi.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.framework.web.service.SysLoginService;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/wxapi/")
@Api(tags = "小程序登录相关接口")
public class WxLoginController {
	
	@Autowired
    private SysLoginService loginService;
	
    /**
     * 你自己的微信小程序APPID
     */

    private final static String AppID = "你自己的微信小程序APPID";
    /**
     * 你自己的微信APP密钥
     */
    private final static String AppSecret = "你自己的微信APP密钥";
	
    /**
     * 测试接口
     * @return
     */
    @ApiOperation("测试接口")
    @GetMapping("test")
    public AjaxResult test(){
        return AjaxResult.success("小程序api调试成功！~");
    }
    
    
    /**
     * 登录时获取的 code（微信官方提供的临时凭证）
     * @param object
     * @return
     */
    @ApiOperation("小程序登录")
    @PostMapping("/login")
    public AjaxResult wxLogin(@RequestBody JSONObject object){
        //微信官方提供的微信小程序登录授权时使用的URL地址
        String url  = "https://api.weixin.qq.com/sns/jscode2session";
        System.out.println(object);

        /**
         * 拼接需要的参数
         * appid = AppID 你自己的微信小程序APPID
         * js_code = AppSecret 你自己的微信APP密钥
         * grant_type=authorization_code = code 微信官方提供的临时凭证
         */
        String params = StrUtil.format("appid={}&secret={}&js_code={}&grant_type=authorization_code", AppID, AppSecret, object.get("code"));
        //开始发起网络请求,若依管理系统自带网络请求工具，直接使用即可
        String res = HttpUtils.sendGet(url,params);
        JSONObject jsonObject = JSON.parseObject(res);
        
        String openid = (String) jsonObject.get("openid");
        if (StrUtil.isEmpty(openid)) {
            return AjaxResult.error("未获取到openid");
        }
        
      return loginService.wxLogin(openid);
        
    }



}

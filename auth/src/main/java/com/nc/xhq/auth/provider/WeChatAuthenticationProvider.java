package com.nc.xhq.auth.provider;

import com.nc.core.common.api.R;
import com.nc.xhq.auth.authentication.WeChatAuthenticationToken;
import com.nc.xhq.auth.entity.info.WxUserInfo;
import com.nc.xhq.auth.service.IWxDealService;
import com.nc.xhq.auth.support.NcAccountDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

/**
 * @description: 实现微信授权登录验证 是否通过的实现(在confing里面需要进行实力化配置)
 * @author: jjxu
 * @time: 2022/1/18
 * @package: com.nc.xhq.auth.provider
 */
@Slf4j
public class WeChatAuthenticationProvider implements AuthenticationProvider {

    private NcAccountDetailService ncUserDetailService;
    private IWxDealService wxDealService;


    public WeChatAuthenticationProvider(NcAccountDetailService ncUserDetailService, IWxDealService wxDealService) {
        this.ncUserDetailService = ncUserDetailService;
        this.wxDealService = wxDealService;
    }

    /* *
     * @Author jjxu 微信验证方式
     * @Description
     * @Date 11:11 2022/1/18
     * @Param [authentication]
     **/
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //验证授权方式token是否为wx
        Assert.isInstanceOf(WeChatAuthenticationToken.class, authentication, () -> "Only WeChatAuthenticationToken is supported");
        //直接强转
        WeChatAuthenticationToken token = (WeChatAuthenticationToken) authentication;
        //通过wxCode 获取解密需要的
        String wxCode = token.getWxCode();
        String encryptedData =token.getEncryptedData();
        String iv = token.getIv();
        log.info("wx login wxCode = {},encryptedData = {},iv={}",wxCode,encryptedData,iv);
        //解密微信信息
        R<WxUserInfo> wR = wxDealService.encryptUserInfo(wxCode, encryptedData, iv);
        //TODO

        WeChatAuthenticationToken result = new WeChatAuthenticationToken(wxCode,encryptedData,iv);
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WeChatAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

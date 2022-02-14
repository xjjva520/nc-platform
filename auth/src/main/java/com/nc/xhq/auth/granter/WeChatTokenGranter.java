package com.nc.xhq.auth.granter;

import com.nc.xhq.auth.authentication.WeChatAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description: 微信授权登录(获取token)
 * @author: jjxu
 * @time: 2022/1/18
 * @package: com.nc.xhq.auth.granter
 */
public class WeChatTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "wechat";
    private final AuthenticationManager authenticationManager;

    protected WeChatTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                                 OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager,String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());

        //前端获取wxCode,然后后台通过wxCode，获取相应的openId，以及用户信息的ssesionKey
        String wxCode = parameters.get("wxCode");
        //前端获取的用户信息密文
        String encryptedData = parameters.get("encryptedData");
        //偏移iv
        String iv = parameters.get("iv");

        parameters.remove("wxCode");
        parameters.remove("encryptedData");
        parameters.remove("iv");

        Authentication userAuth = new WeChatAuthenticationToken(wxCode, encryptedData,iv); // 未认证状态
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        try {
            userAuth = this.authenticationManager.authenticate(userAuth); // 认证中
        } catch (Exception e) {
            throw new InvalidGrantException(e.getMessage());
        }

        if (userAuth != null && userAuth.isAuthenticated()) { // 认证成功
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, userAuth);
        } else { // 认证失败
            throw new InvalidGrantException("Could not authenticate wxCode: " + wxCode);
        }
    }
}

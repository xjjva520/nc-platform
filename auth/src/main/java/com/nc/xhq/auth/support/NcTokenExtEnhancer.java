package com.nc.xhq.auth.support;

import com.nc.auth.core.props.TokenProperties;
import com.nc.auth.core.utils.NcAuthUtil;
import com.nc.auth.core.utils.TokenUtil;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/27
 * @package: com.nc.xhq.auth.support
 */
public class NcTokenExtEnhancer implements TokenEnhancer {

    private  JwtAccessTokenConverter jwtAccessTokenConverter;
    private  TokenProperties tokenProperties;


    public NcTokenExtEnhancer(JwtAccessTokenConverter jwtAccessTokenConverter, TokenProperties tokenProperties) {
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.tokenProperties = tokenProperties;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        NcAccountDetails details = (NcAccountDetails) authentication.getUserAuthentication().getPrincipal();

        //token参数增强
        Map<String, Object> info = new HashMap<>(16);
        //客户端验证，则无需进行处理
        if (authentication.isClientOnly()) {
            info.put("clientId", authentication.getPrincipal().toString());
            info.put("loginUserId", authentication.getPrincipal().toString());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        } else {
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(details.getPrincipal().toMap());
        }
        //token状态设置
        if (tokenProperties.getState()) {
            OAuth2AccessToken oAuth2AccessToken = jwtAccessTokenConverter.enhance(accessToken, authentication);
            String tokenValue = oAuth2AccessToken.getValue();
            TokenUtil.addAccessToken(details.getPrincipal(), tokenValue, accessToken.getExpiresIn());
        }
        return accessToken;
    }
}

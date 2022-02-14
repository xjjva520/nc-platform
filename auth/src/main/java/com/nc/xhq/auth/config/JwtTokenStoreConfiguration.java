package com.nc.xhq.auth.config;

import com.nc.auth.core.props.TokenProperties;
import com.nc.xhq.auth.support.NcTokenExtEnhancer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/27
 * @package: com.nc.xhq.auth.config
 */
@Configuration
public class JwtTokenStoreConfiguration {

    /**
     * 使用jwtTokenStore存储token
     */
    @Bean
    public TokenStore jwtTokenStore(TokenProperties tokenProperties) {
        return new JwtTokenStore(jwtAccessTokenConverter(tokenProperties));
    }

    /**
     * 用于生成jwt
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(TokenProperties tokenProperties) {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(tokenProperties.getSignKey());
        return accessTokenConverter;
    }

    /**
     * 用于扩展jwt--存入redis变成有状态
     */
    @Bean
    @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
    public TokenEnhancer jwtTokenEnhancer(JwtAccessTokenConverter jwtAccessTokenConverter, TokenProperties jwtProperties) {
        return new NcTokenExtEnhancer(jwtAccessTokenConverter, jwtProperties);
    }

}

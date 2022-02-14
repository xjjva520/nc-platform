package com.nc.xhq.auth.config;

import com.nc.component.redis.client.RedisOprClient;
import com.nc.xhq.auth.granter.TokenGranterUtil;
import com.nc.xhq.auth.support.NcAccountDetailService;
import com.nc.xhq.auth.support.NcClientDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 授权认证服务(实现AuthorizationServerConfigurerAdapter)
 * @author: jjxu
 * @time: 2022/1/12
 * @package: com.nc.xhq.auth.config
 */
@Configuration
@EnableAuthorizationServer
public class NcAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private NcAccountDetailService ncAccountDetailService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisOprClient redisOprClient;

    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    @Autowired
    private  JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private  WebResponseExceptionTranslator webResponseExceptionTranslator;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        NcClientDetailService clientDetailService = new NcClientDetailService(dataSource,redisOprClient);
        clients.withClientDetails(clientDetailService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
         //获取自定义tokenGranter
        TokenGranter tokenGranter = TokenGranterUtil.getCompositeTokenGranter(authenticationManager, ncAccountDetailService, endpoints);

        //配置端点
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(ncAccountDetailService)
                .exceptionTranslator(webResponseExceptionTranslator)//认证异常处理器
                .tokenGranter(tokenGranter);


        //扩展token返回结果
        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancerList = new ArrayList<>();
            enhancerList.add(jwtTokenEnhancer);
            enhancerList.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(enhancerList);
            //jwt增强
            endpoints.tokenEnhancer(tokenEnhancerChain).accessTokenConverter(jwtAccessTokenConverter);
        }
    }
}

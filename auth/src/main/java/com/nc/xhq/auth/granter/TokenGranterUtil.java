package com.nc.xhq.auth.granter;

import com.nc.xhq.auth.enums.GrantTypes;
import com.nc.xhq.auth.support.NcAccountDetailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 获取tokenGranter ---CompositeTokenGranter (将所有授权模式添加到集合)
 * @author: jjxu
 * @time: 2022/1/25
 * @package: com.nc.xhq.auth.granter
 */
public class TokenGranterUtil {

    public static TokenGranter getCompositeTokenGranter(final AuthenticationManager authenticationManager, final NcAccountDetailService ncAccountDetailService,
                                                        final AuthorizationServerEndpointsConfigurer endpoints){
        // 声明认证方式集合
        List<TokenGranter> granters = new ArrayList<>();
        //密码认证
        granters.add(new NcPasswordTokenGranter(authenticationManager, endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),ncAccountDetailService, endpoints.getOAuth2RequestFactory(), GrantTypes.password.toString()));

        //微信认证
        granters.add(new WeChatTokenGranter( endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),endpoints.getOAuth2RequestFactory(),authenticationManager,GrantTypes.wechat.toString()));

        //刷新token
        granters.add(new RefreshTokenGranter(endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),endpoints.getOAuth2RequestFactory()));

        //授权码模式
        granters.add(new AuthorizationCodeTokenGranter(endpoints.getTokenServices(),endpoints.getAuthorizationCodeServices(),
                endpoints.getClientDetailsService(),endpoints.getOAuth2RequestFactory()));
        // 组合tokenGranter集合
        return new CompositeTokenGranter(granters);
    }
}

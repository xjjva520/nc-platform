package com.nc.xhq.auth.granter;

import com.nc.xhq.auth.support.NcAccountDetailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/27
 * @package: com.nc.xhq.auth.granter
 */
public class NcPasswordTokenGranter extends ResourceOwnerPasswordTokenGranter {

    private NcAccountDetailService ncAccountDetailService;

    public NcPasswordTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(authenticationManager, tokenServices, clientDetailsService, requestFactory);
    }

    protected NcPasswordTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, NcAccountDetailService ncAccountDetailService,
                                     OAuth2RequestFactory requestFactory, String grantType) {
        super(authenticationManager, tokenServices, clientDetailsService, requestFactory, grantType);
        this.ncAccountDetailService = ncAccountDetailService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String username = parameters.get("username");
        try{
            OAuth2Authentication oAuth2Authentication = super.getOAuth2Authentication(client, tokenRequest);
            ncAccountDetailService.removeErrorTime(client.getClientId(),username);
            return oAuth2Authentication;
        }catch (InvalidGrantException e){
            ncAccountDetailService.addErrorTime(client.getClientId(),username);
            throw new InvalidClientException("用户名或密码错误");
        }
    }
}

package com.nc.xhq.auth.support;

import com.nc.auth.core.entity.NcPrincipal;
import com.nc.component.redis.client.RedisOprClient;
import com.nc.xhq.auth.props.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @description: grantType为passWord 必须要实现UserDetailService接口
 * @author: jjxu
 * @time: 2022/1/26
 * @package: com.nc.xhq.auth.service
 */
@Service
public class NcAccountDetailService implements UserDetailsService {

    @Autowired
    private  RedisOprClient redisOprClient;
    @Autowired
    private AuthProperties authProperties;

    private static long monthTime = 30L*24L*60L * 60L;
    private final static String LOGIN_FAIL_CONTROL_KEY = "login_fail_control:%s:%s";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NcPrincipal principal =  new NcPrincipal();
        principal.setLoginUserName("xujianjian");
        principal.setClientId("APP");
        principal.setLoginUserId("UUID");
        principal.setRealName("sadasd");
        principal.setAppId("nc-app");
        NcAccountDetails accountDetails = new NcAccountDetails("xujianjian", "{nc}bef89eae08dc6da614f08d6da6ed7c23a96f620c", true, true, true, true, AuthorityUtils
                .commaSeparatedStringToAuthorityList("123"),principal);

        return accountDetails;
    }


    //增加控制密码输入错误次数
    public void addErrorTime(String clientId ,String userName){
        String key = String.format(LOGIN_FAIL_CONTROL_KEY,clientId,userName);
        redisOprClient.incrBy(key, 1);
        redisOprClient.expire(key,authProperties.getLoginLockTime(),TimeUnit.MINUTES);
    }

    //登录成功移除错误次数
    public void removeErrorTime(String clientId ,String userName){
        String key = String.format(LOGIN_FAIL_CONTROL_KEY,clientId,userName);
        this.redisOprClient.deleteKey(key);
    }
}

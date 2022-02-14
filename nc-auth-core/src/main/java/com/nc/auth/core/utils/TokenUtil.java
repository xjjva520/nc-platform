package com.nc.auth.core.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.nc.auth.core.entity.NcPrincipal;
import com.nc.auth.core.props.TokenProperties;
import com.nc.core.common.utils.SpringApplicationUtil;
import jodd.util.StringUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class TokenUtil {
    private static final String TOKEN_CACHE = "nc:token";
    private static final String TOKEN_KEY = "state";
    private static RedisTemplate<String, Object> redisTemplate;

     static {
         redisTemplate = (RedisTemplate<String, Object>) SpringApplicationUtil.getBean("redisTemplate");
    }

    public static String getAccessToken(NcPrincipal ncPrincipal, String accessToken) {
        return String.valueOf(redisTemplate.opsForValue().get(getAccessTokenKey(ncPrincipal, accessToken)));
    }

    public static void addAccessToken(NcPrincipal principal, String accessToken, int expire) {
        redisTemplate.delete(getAccessTokenKey(principal, accessToken));
        redisTemplate.opsForValue().set(getAccessTokenKey(principal, accessToken), accessToken, (long)expire, TimeUnit.SECONDS);
    }

    public static void removeAccessTokenHaveState(NcPrincipal principal) {
        removeAccessToken(principal, (String)null);
    }

    public static void removeAccessTokenNotHaveState(NcPrincipal principal,String lastToken) {
        removeAccessToken(principal, lastToken);
    }

    private static void removeAccessToken(NcPrincipal principal, String accessToken) {
        redisTemplate.delete(getAccessTokenKey(principal, accessToken));
    }

    private static String getAccessTokenKey(NcPrincipal ncPrincipal, String accessToken) {
        String key = TOKEN_CACHE.concat("::").concat(TOKEN_KEY).concat(":").concat(ncPrincipal.getClientId()).concat(":").concat(ncPrincipal.getAppId()).concat(":");
        TokenProperties tokenProperties = NcAuthUtil.getTokenProperties();
        return !tokenProperties.getSingleClientLogin() && !StringUtil.isEmpty(accessToken) ? key.concat(accessToken) : key.concat(ncPrincipal.getLoginUserId());
    }
}

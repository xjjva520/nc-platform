package com.nc.auth.core.utils;

import com.nc.auth.core.entity.NcPrincipal;
import com.nc.auth.core.props.Constans;
import com.nc.auth.core.props.TokenProperties;
import com.nc.core.common.utils.ObjectUtil;
import com.nc.core.common.utils.SpringApplicationUtil;
import com.nc.core.common.utils.WebUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/19
 * @package: com.nc.auth.core.utils
 */
public class NcAuthUtil {

    private final static int AUTH_LENGTH = 7;

    private static TokenProperties tokenProperties;

    public static TokenProperties getTokenProperties() {
        if (tokenProperties == null) {
            tokenProperties = SpringApplicationUtil.getBean(TokenProperties.class);
        }
        return tokenProperties;
    }

    public static NcPrincipal getAccount() {
        HttpServletRequest request = WebUtil.getRequest();
        if (request == null) {
            return null;
        } else {
            Object attribute = request.getAttribute("_NC_ACCOUNT_REQUEST_ATTR_");
            if (attribute == null) {
                String auth = request.getHeader(Constans.token_head);
                attribute = getAccount(auth);
                if (attribute != null) {
                    request.setAttribute("_NC_ACCOUNT_REQUEST_ATTR_", attribute);
                }
            }

            return (NcPrincipal)attribute;
        }
    }

    public static NcPrincipal getAccount(String access_token) {
        Claims claims = getClaims(access_token);
        return claims == null ? null : new NcPrincipal(claims);
    }

    public static String getBase64Security() {
        return Base64.getEncoder().encodeToString(getTokenProperties().getSignKey().getBytes(StandardCharsets.UTF_8));
    }

    public static String getToken(String auth) {
        if (auth != null && auth.length() > AUTH_LENGTH) {
            String headStr = auth.substring(0, 6).toLowerCase();
            if (headStr.compareTo("bearer") == 0) {
                auth = auth.substring(7);
            }
            return auth;
        } else {
            return null;
        }
    }

    public static Claims parseJWT(String jsonWebToken) {
        try {
            return (Claims) Jwts.parserBuilder().setSigningKey(Base64.getDecoder().decode(getBase64Security())).build().parseClaimsJws(jsonWebToken).getBody();
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Claims getClaims(String access_token) {
        String token = getToken(access_token);
        Claims claims = null;
        if (StringUtils.isNotBlank(token)) {
            claims = parseJWT(token);
        }
        //如果有redis验证，则需要进行redis进行验证，比对sino-auth
        if (ObjectUtil.isNotEmpty(claims) && getTokenProperties().getState()) {
            String accessToken = TokenUtil.getAccessToken(new NcPrincipal(claims), token);
            if (!token.equalsIgnoreCase(accessToken)) {
                return null;
            }
        }
        return claims;
    }
}

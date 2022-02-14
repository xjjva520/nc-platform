package com.nc.auth.core.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/20
 * @package: com.nc.auth.core.props
 */
@ConfigurationProperties("nc.oauth.token")
public class TokenProperties {

    //token状态
    private Boolean state = Boolean.FALSE;

    //只允许一个端登录---需要与状态一起配置成true
    private Boolean singleClientLogin = Boolean.FALSE;

    //签名串（32位长度串）
    private String signKey = "4xtfgddQuQ14fKRrCdrD81SUqCu0WFvZ";

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getSingleClientLogin() {
        return singleClientLogin;
    }

    public void setSingleClientLogin(Boolean singleClientLogin) {
        this.singleClientLogin = singleClientLogin;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }
}

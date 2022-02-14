package com.nc.xhq.auth.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/27
 * @package: com.nc.xhq.auth.props
 */
@ConfigurationProperties(prefix = "nc.auth")
@Configuration
public class AuthProperties {

    private long loginLockTime;

    private long loginErrorTimes;

    public long getLoginLockTime() {
        return loginLockTime;
    }

    public void setLoginLockTime(long loginLockTime) {
        this.loginLockTime = loginLockTime;
    }

    public long getLoginErrorTimes() {
        return loginErrorTimes;
    }

    public void setLoginErrorTimes(long loginErrorTimes) {
        this.loginErrorTimes = loginErrorTimes;
    }
}

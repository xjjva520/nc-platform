package com.nc.core.common.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/20
 * @package: com.nc.auth.core.props
 */
@ConfigurationProperties(prefix = "nc.okhttp")
public class OkHttpConfigurationProperties {

    private Long connectTimeOut = 5L;

    private Long readTimeOut = 5L;

    private Long writeTimeOut = 5L;

    public Long getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(Long connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public Long getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(Long readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public Long getWriteTimeOut() {
        return writeTimeOut;
    }

    public void setWriteTimeOut(Long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }
}

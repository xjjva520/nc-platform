package com.nc.api.crypto.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/2/10
 * @package: com.nc.api.crypto.props
 */
@ConfigurationProperties("nc.crypto")
public class CryptoProperties {

    //控制是否打开加密
    private Boolean enabled = false;

    private String aesKey;

    private String desKey;

    private String rsaKey;

    //控制是否对请求正常json是否需要解密
    private Boolean isSkipJson = true;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getDesKey() {
        return desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }

    public String getRsaKey() {
        return rsaKey;
    }

    public void setRsaKey(String rsaKey) {
        this.rsaKey = rsaKey;
    }

    public Boolean getSkipJson() {
        return isSkipJson;
    }

    public void setSkipJson(Boolean skipJson) {
        isSkipJson = skipJson;
    }
}

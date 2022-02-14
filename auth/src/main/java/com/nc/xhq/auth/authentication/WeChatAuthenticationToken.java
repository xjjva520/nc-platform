package com.nc.xhq.auth.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/18
 * @package: com.nc.xhq.auth.authentication
 */
public class WeChatAuthenticationToken extends AbstractAuthenticationToken {

    private String wxCode;

    private String encryptedData;

    private String iv;

    public WeChatAuthenticationToken( String wxCode, String encryptedData, String iv) {
        super((Collection) null);
        this.wxCode = wxCode;
        this.encryptedData = encryptedData;
        this.iv = iv;
    }

    public WeChatAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String wxCode, String encryptedData, String iv) {
        super(authorities);
        this.wxCode = wxCode;
        this.encryptedData = encryptedData;
        this.iv = iv;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}

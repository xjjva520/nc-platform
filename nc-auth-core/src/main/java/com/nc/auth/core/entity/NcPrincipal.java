package com.nc.auth.core.entity;

import io.jsonwebtoken.Claims;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 登录信息扩展信息封装
 * @author: jjxu
 * @time: 2022/1/19
 * @package: com.nc.auth.core.entity
 */
public class NcPrincipal implements Serializable {


    private static final long serialVersionUID = -4536766900453159273L;

    //客户端id
    private String clientId;

    //登录用户Id
    private String loginUserId;

    //登录用户名
    private String loginUserName;

    //账户信息
    private String account;

    //昵称
    private String nickName;

    //真实姓名
    private String realName;

    //用户头像
    private String avatarUrl;

    //角色Id
    private String roleId;

    //角色名称
    private String roleName;

    //app的Id
    private String appId;

    //扩展域
    private Map<String, Object> ext = new HashMap();

    public NcPrincipal() {
    }

    public NcPrincipal(Claims claims) {
        if (claims != null) {
            this.setClientId(toStr(claims.get("clientId")));
            this.setLoginUserId(toStr(claims.get("loginUserId")));
            this.setLoginUserName(toStr(claims.get("loginUserName")));
            this.setNickName(toStr(claims.get("nickName")));
            this.setRealName(toStr(claims.get("realName")));
            this.setRoleId(toStrWithEmpty(claims.get("roleId")));
            this.setRoleName(toStr(claims.get("roleName")));
            this.setAvatarUrl(toStr(claims.get("avatarUrl")));
            this.setAccount(toStr(claims.get("account")));
            this.setAppId(toStr(claims.get("appId")));
            claims.keySet().forEach((key) -> {
                this.ext.put(key, claims.get(key));
            });
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap(this.ext);
        map.put("clientId", this.getClientId());
        map.put("loginUserId", this.getLoginUserId());
        map.put("loginUserName", this.getLoginUserName());
        map.put("nickName", this.getNickName());
        map.put("avatarUrl", this.getAvatarUrl());
        map.put("account", this.getAccount());
        map.put("roleId", this.getRoleId());
        map.put("roleName", this.getRoleName());
        map.put("realName", this.getRealName());
        map.put("appId", this.getAppId());
        return map;
    }

    public Object get(String key) {
        return this.toMap().get(key);
    }



    private static String toStr(Object str) {
        return null != str && !str.equals("null") ? String.valueOf(str) : "";
    }


    private static String toStrWithEmpty(Object str) {
        return null != str && !str.equals("null") && !str.equals("") ? String.valueOf(str) : "-1";
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Map<String, Object> getExt() {
        return ext;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}

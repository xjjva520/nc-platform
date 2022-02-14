package com.nc.core.common.api;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/19
 * @package: com.nc.auth.core.api
 */
public enum ResultCode {

    SUCCESS("0000"),

    FAILURE("10000"),

    INTERNAL_SERVER_ERROR("500"),

    //服务请求调用异常----
    SERVER_REQUEST_ERROR("401");

     final String code;

    public String getCode() {
        return this.code;
    }

    private ResultCode(final String code) {
        this.code = code;
    }
}

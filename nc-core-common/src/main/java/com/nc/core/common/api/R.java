package com.nc.core.common.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Optional;

public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private Boolean success;

    private T data;

    private String msg;


    private Long ts;

    private R(String code, T data, String msg) {
        this.success = true;
        this.ts = System.currentTimeMillis();
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = ResultCode.SUCCESS.getCode().equals(code);
    }

    private R(String code, String msg) {
        this(code, (T) null, msg);
    }

    public boolean isSuccess() {
        return (Boolean)Optional.of(this).map((x) -> {
            return ObjectUtils.nullSafeEquals(ResultCode.SUCCESS.getCode(), x.code);
        }).orElse(Boolean.FALSE);
    }

    @JsonIgnore
    public boolean isFailed() {
        return !this.isSuccess();
    }

    public static boolean isSuccess(@Nullable R<?> result) {
        return (Boolean)Optional.ofNullable(result).map((x) -> {
            return ObjectUtils.nullSafeEquals(ResultCode.SUCCESS.getCode(), x.code);
        }).orElse(Boolean.FALSE);
    }

    public static boolean isNotSuccess(@Nullable R<?> result) {
        return !isSuccess(result);
    }

    public static <T> R<T> data(T data) {
        return data(data, "操作成功");
    }

    public static <T> R<T> data(T data, String msg) {
        return data(ResultCode.SUCCESS.getCode(), data, msg);
    }

    public static <T> R<T> data(String code, T data, String msg) {
        return new R(code, data, data == null ? "暂无承载数据" : msg);
    }
    public static <T> R<T> fail(String msg) {
        return new R(ResultCode.FAILURE.getCode(), msg);
    }

    public T getData() {
        return this.data;
    }

    public String getMsg() {
        return this.msg;
    }

    public Long getTs() {
        return this.ts;
    }


    public void setData(final T data) {
        this.data = data;
    }

    public R() {
        this.success = true;
        this.ts = System.currentTimeMillis();
    }
}

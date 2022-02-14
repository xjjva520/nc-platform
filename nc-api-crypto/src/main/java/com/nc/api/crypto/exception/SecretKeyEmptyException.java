package com.nc.api.crypto.exception;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/2/10
 * @package: com.nc.api.crypto.exception
 */
public class SecretKeyEmptyException extends RuntimeException{

    public SecretKeyEmptyException(String message) {
        super(message);
    }
}

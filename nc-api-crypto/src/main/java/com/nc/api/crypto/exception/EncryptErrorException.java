package com.nc.api.crypto.exception;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/2/10
 * @package: com.nc.api.crypto.exception
 */
public class EncryptErrorException extends RuntimeException{

    public EncryptErrorException(String message) {
        super(message);
    }
}

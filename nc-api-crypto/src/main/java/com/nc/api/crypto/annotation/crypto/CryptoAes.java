package com.nc.api.crypto.annotation.crypto;

import com.nc.api.crypto.annotation.decrypt.Decrypt;
import com.nc.api.crypto.annotation.encrypt.Encrypt;
import com.nc.api.crypto.enums.CryptoType;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Encrypt(CryptoType.AES)
@Decrypt(CryptoType.AES)
public @interface CryptoAes {
}

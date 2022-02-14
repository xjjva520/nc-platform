package com.nc.api.crypto.annotation.encrypt;


import com.nc.api.crypto.enums.CryptoType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Encrypt(CryptoType.DES)
public @interface EncryptDes {

    @AliasFor(
            annotation = Encrypt.class
    )
    String secretKey() default "";
}

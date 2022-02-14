package com.nc.api.crypto.annotation.encrypt;


import com.nc.api.crypto.enums.CryptoType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Encrypt(CryptoType.AES)
public @interface EncryptAes {

    @AliasFor(
            annotation = Encrypt.class
    )
    String secretKey() default "";
}

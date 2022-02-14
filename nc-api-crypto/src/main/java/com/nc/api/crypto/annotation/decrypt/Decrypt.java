package com.nc.api.crypto.annotation.decrypt;

import com.nc.api.crypto.enums.CryptoType;

import java.lang.annotation.*;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/2/9
 * @package: com.nc.api.crypto.annotation.encrypt
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Decrypt {

    CryptoType value();

    String secretKey() default "";
}

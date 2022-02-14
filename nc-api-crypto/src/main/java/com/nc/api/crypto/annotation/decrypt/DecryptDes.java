package com.nc.api.crypto.annotation.decrypt;

import com.nc.api.crypto.enums.CryptoType;
import org.springframework.core.annotation.AliasFor;

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
@Decrypt(value = CryptoType.DES)
public @interface DecryptDes {

    @AliasFor(
            annotation = Decrypt.class
    )
    String secretKey() default "";
}

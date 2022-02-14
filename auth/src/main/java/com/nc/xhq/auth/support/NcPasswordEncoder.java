package com.nc.xhq.auth.support;

import com.nc.core.common.utils.DigestUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @description: 密码验证 怎么进行加密后进行比对
 * @author: jjxu
 * @time: 2022/1/27
 * @package: com.nc.xhq.auth.support
 */
public class NcPasswordEncoder implements PasswordEncoder {

    /* *
     * @Author jjxu
     * @Description 密码的解密--客户端传的密文是加密的
     * @Date 16:05 2022/1/27
     * @Param [rawPassword]
     **/
    @Override
    public String encode(CharSequence rawPassword) {
        return DigestUtil.hex((String) rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }


}

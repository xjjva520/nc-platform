package com.nc.core.common.utils;

import org.springframework.util.Base64Utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/19
 * @package: com.nc.auth.core.utils
 */
public class Base64Util extends Base64Utils {

    public static String encode(String value) {
        return encode(value, StandardCharsets.UTF_8);
    }

    public static String encode(String value, Charset charset) {
        byte[] val = value.getBytes(charset);
        return new String(encode(val), charset);
    }

    public static String encodeUrlSafe(String value) {
        return encodeUrlSafe(value, StandardCharsets.UTF_8);
    }

    public static String encodeUrlSafe(String value, Charset charset) {
        byte[] val = value.getBytes(charset);
        return new String(encodeUrlSafe(val), charset);
    }

    public static String decode(String value) {
        return decode(value, StandardCharsets.UTF_8);
    }

    public static String decode(String value, Charset charset) {
        byte[] val = value.getBytes(charset);
        byte[] decodedValue = decode(val);
        return new String(decodedValue, charset);
    }

    public static String decodeUrlSafe(String value) {
        return decodeUrlSafe(value, StandardCharsets.UTF_8);
    }

    public static String decodeUrlSafe(String value, Charset charset) {
        byte[] val = value.getBytes(charset);
        byte[] decodedValue = decodeUrlSafe(val);
        return new String(decodedValue, charset);
    }
}

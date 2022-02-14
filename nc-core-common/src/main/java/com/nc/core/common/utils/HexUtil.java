package com.nc.core.common.utils;

import org.springframework.lang.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/19
 * @package: com.nc.auth.core.utils
 */
public class HexUtil {
    public static final Charset DEFAULT_CHARSET;
    private static final byte[] DIGITS_LOWER;
    private static final byte[] DIGITS_UPPER;

    public HexUtil() {
    }

    public static byte[] encode(byte[] data) {
        return encode(data, true);
    }

    public static byte[] encode(byte[] data, boolean toLowerCase) {
        return encode(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    private static byte[] encode(byte[] data, byte[] digits) {
        int len = data.length;
        byte[] out = new byte[len << 1];
        int i = 0;

        for(int var5 = 0; i < len; ++i) {
            out[var5++] = digits[(240 & data[i]) >>> 4];
            out[var5++] = digits[15 & data[i]];
        }

        return out;
    }

    public static String encodeToString(byte[] data, boolean toLowerCase) {
        return new String(encode(data, toLowerCase), DEFAULT_CHARSET);
    }

    public static String encodeToString(byte[] data) {
        return new String(encode(data), DEFAULT_CHARSET);
    }

    @Nullable
    public static String encodeToString(@Nullable String data) {
        return StringUtil.isBlank(data) ? null : encodeToString(data.getBytes(DEFAULT_CHARSET));
    }

    @Nullable
    public static byte[] decode(@Nullable String data) {
        return StringUtil.isBlank(data) ? null : decode(data.getBytes(DEFAULT_CHARSET));
    }

    @Nullable
    public static String decodeToString(@Nullable String data) {
        byte[] decodeBytes = decode(data);
        return decodeBytes == null ? null : new String(decodeBytes, DEFAULT_CHARSET);
    }

    public static byte[] decode(byte[] data) {
        int len = data.length;
        if ((len & 1) != 0) {
            throw new IllegalArgumentException("hexBinary needs to be even-length: " + len);
        } else {
            byte[] out = new byte[len >> 1];
            int i = 0;

            for(int j = 0; j < len; ++i) {
                int f = toDigit(data[j], j) << 4;
                ++j;
                f |= toDigit(data[j], j);
                ++j;
                out[i] = (byte)(f & 255);
            }

            return out;
        }
    }

    private static int toDigit(byte b, int index) {
        int digit = Character.digit(b, 16);
        if (digit == -1) {
            throw new IllegalArgumentException("Illegal hexadecimal byte " + b + " at index " + index);
        } else {
            return digit;
        }
    }

    static {
        DEFAULT_CHARSET = StandardCharsets.UTF_8;
        DIGITS_LOWER = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
        DIGITS_UPPER = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
    }
}

package com.nc.core.common.utils;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/19
 * @package: com.nc.auth.core.utils
 */
public class DatatypeConverterUtil {

    public static byte[] parseHexBinary(String hexStr) {
        int len = hexStr.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("hexBinary needs to be even-length: " + hexStr);
        } else {
            byte[] out = new byte[len / 2];

            for(int i = 0; i < len; i += 2) {
                int h = hexToBin(hexStr.charAt(i));
                int l = hexToBin(hexStr.charAt(i + 1));
                if (h == -1 || l == -1) {
                    throw new IllegalArgumentException("contains illegal character for hexBinary: " + hexStr);
                }

                out[i / 2] = (byte)(h * 16 + l);
            }

            return out;
        }
    }

    private static int hexToBin(char ch) {
        if ('0' <= ch && ch <= '9') {
            return ch - 48;
        } else if ('A' <= ch && ch <= 'F') {
            return ch - 65 + 10;
        } else {
            return 'a' <= ch && ch <= 'f' ? ch - 97 + 10 : -1;
        }
    }
}

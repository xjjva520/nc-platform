package com.nc.core.common.utils;

import org.springframework.lang.Nullable;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/19
 * @package: com.nc.auth.core.utils
 */
public class DesUtil {
    public static final String DES_ALGORITHM = "DES";
    public static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public DesUtil() {
    }

    public static String genDesKey() {
        return StringUtil.random(16);
    }

    public static String encryptToHex(byte[] data, String password) {
        return HexUtil.encodeToString(encrypt(data, password));
    }

    @Nullable
    public static String encryptToHex(@Nullable String data, String password) {
        if (StringUtil.isBlank(data)) {
            return null;
        } else {
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            return encryptToHex(dataBytes, password);
        }
    }

    @Nullable
    public static String decryptFormHex(@Nullable String data, String password) {
        if (StringUtil.isBlank(data)) {
            return null;
        } else {
            byte[] hexBytes = HexUtil.decode(data);
            return new String(decrypt(hexBytes, password), StandardCharsets.UTF_8);
        }
    }

    public static String encryptToBase64(byte[] data, String password) {
        return Base64Util.encodeToString(encrypt(data, password));
    }

    @Nullable
    public static String encryptToBase64(@Nullable String data, String password) {
        if (StringUtil.isBlank(data)) {
            return null;
        } else {
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            return encryptToBase64(dataBytes, password);
        }
    }

    public static byte[] decryptFormBase64(byte[] data, String password) {
        byte[] dataBytes = Base64Util.decode(data);
        return decrypt(dataBytes, password);
    }

    @Nullable
    public static String decryptFormBase64(@Nullable String data, String password) {
        if (StringUtil.isBlank(data)) {
            return null;
        } else {
            byte[] dataBytes = Base64Util.decodeFromString(data);
            return new String(decrypt(dataBytes, password), StandardCharsets.UTF_8);
        }
    }

    public static byte[] encrypt(byte[] data, byte[] desKey) {
        return des(data, desKey, 1);
    }

    public static byte[] encrypt(byte[] data, String desKey) {
        return encrypt(data, ((String) Objects.requireNonNull(desKey)).getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] decrypt(byte[] data, byte[] desKey) {
        return des(data, desKey, 2);
    }

    public static byte[] decrypt(byte[] data, String desKey) {
        return decrypt(data, ((String)Objects.requireNonNull(desKey)).getBytes(StandardCharsets.UTF_8));
    }

    private static byte[] des(byte[] data, byte[] desKey, int mode) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Cipher cipher = Cipher.getInstance("DES");
            DESKeySpec desKeySpec = new DESKeySpec(desKey);
            cipher.init(mode, keyFactory.generateSecret(desKeySpec), SECURE_RANDOM);
            return cipher.doFinal(data);
        } catch (Exception var6) {
            throw ExceptionsUtil.unchecked(var6);
        }
    }
}

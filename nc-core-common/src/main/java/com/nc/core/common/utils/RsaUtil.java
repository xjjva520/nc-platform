package com.nc.core.common.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.*;
import java.util.Objects;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/19
 * @package: com.nc.auth.core.utils
 */
public class RsaUtil {
    public static final String RSA_ALGORITHM = "RSA";
    public static final String RSA_PADDING = "RSA/ECB/PKCS1Padding";

    public RsaUtil() {
    }

    public static KeyPair genKeyPair() {
        return genKeyPair(1024);
    }

    public static KeyPair genKeyPair(int keySize) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(keySize);
            return keyPairGen.generateKeyPair();
        } catch (NoSuchAlgorithmException var2) {
            throw ExceptionsUtil.unchecked(var2);
        }
    }

    public static PrivateKey generatePrivateKey(String modulus, String exponent) {
        return generatePrivateKey(new BigInteger(modulus), new BigInteger(exponent));
    }

    public static PrivateKey generatePrivateKey(BigInteger modulus, BigInteger exponent) {
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, exponent);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException var4) {
            throw ExceptionsUtil.unchecked(var4);
        }
    }

    public static PublicKey generatePublicKey(String modulus, String exponent) {
        return generatePublicKey(new BigInteger(modulus), new BigInteger(exponent));
    }

    public static PublicKey generatePublicKey(BigInteger modulus, BigInteger exponent) {
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException var4) {
            throw ExceptionsUtil.unchecked(var4);
        }
    }

    public static PublicKey getPublicKey(String base64PubKey) {
        Objects.requireNonNull(base64PubKey, "base64 public key is null.");
        byte[] keyBytes = Base64Utils.decodeFromString(base64PubKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException var4) {
            throw ExceptionsUtil.unchecked(var4);
        }
    }

    public static String getPublicKeyToBase64(String base64PubKey) {
        PublicKey publicKey = getPublicKey(base64PubKey);
        return getKeyString(publicKey);
    }

    public static PrivateKey getPrivateKey(String base64PriKey) {
        Objects.requireNonNull(base64PriKey, "base64 private key is null.");
        byte[] keyBytes = Base64Utils.decodeFromString(base64PriKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException var4) {
            throw ExceptionsUtil.unchecked(var4);
        }
    }

    public static String getKeyString(Key key) {
        return Base64Utils.encodeToString(key.getEncoded());
    }

    public static String getPrivateKeyToBase64(String base64PriKey) {
        PrivateKey privateKey = getPrivateKey(base64PriKey);
        return getKeyString(privateKey);
    }

    public static byte[] encrypt(String base64PublicKey, byte[] data) {
        return encrypt(getPublicKey(base64PublicKey), data);
    }

    public static byte[] encrypt(PublicKey publicKey, byte[] data) {
        return rsa(publicKey, data, 1);
    }

    public static byte[] encryptByPrivateKey(String base64PrivateKey, byte[] data) {
        return encryptByPrivateKey(getPrivateKey(base64PrivateKey), data);
    }

    public static String encryptByPrivateKeyToBase64(String base64PrivateKey, byte[] data) {
        return Base64Util.encodeToString(encryptByPrivateKey(base64PrivateKey, data));
    }

    public static byte[] encryptByPrivateKey(PrivateKey privateKey, byte[] data) {
        return rsa(privateKey, data, 1);
    }

    @Nullable
    public static String encryptToBase64(String base64PublicKey, @Nullable String data) {
        return StringUtil.isBlank(data) ? null : Base64Utils.encodeToString(encrypt(base64PublicKey, data.getBytes(StandardCharsets.UTF_8)));
    }

    public static byte[] decrypt(String base64PrivateKey, byte[] data) {
        return decrypt(getPrivateKey(base64PrivateKey), data);
    }

    public static byte[] decryptByPublicKey(String base64publicKey, byte[] data) {
        return decryptByPublicKey(getPublicKey(base64publicKey), data);
    }

    public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
        return rsa(privateKey, data, 2);
    }

    public static byte[] decryptByPublicKey(PublicKey publicKey, byte[] data) {
        return rsa(publicKey, data, 2);
    }

    private static byte[] rsa(Key key, byte[] data, int mode) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(mode, key);
            return cipher.doFinal(data);
        } catch (Exception var4) {
            throw ExceptionsUtil.unchecked(var4);
        }
    }

    public static byte[] decryptByPublicKeyFromBase64(String base64PublicKey, byte[] base64Data) {
        return decryptByPublicKey(getPublicKey(base64PublicKey), base64Data);
    }

    @Nullable
    public static String decryptFromBase64(String base64PrivateKey, @Nullable String base64Data) {
        return StringUtil.isBlank(base64Data) ? null : new String(decrypt(base64PrivateKey, Base64Utils.decodeFromString(base64Data)), StandardCharsets.UTF_8);
    }

    public static byte[] decryptFromBase64(String base64PrivateKey, byte[] base64Data) {
        return decrypt(base64PrivateKey, Base64Utils.decode(base64Data));
    }

    @Nullable
    public static String decryptByPublicKeyFromBase64(String base64PublicKey, @Nullable String base64Data) {
        return StringUtil.isBlank(base64Data) ? null : new String(decryptByPublicKeyFromBase64(base64PublicKey, Base64Utils.decodeFromString(base64Data)), StandardCharsets.UTF_8);
    }
}

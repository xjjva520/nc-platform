package com.nc.api.crypto.utils;

import com.nc.api.crypto.annotation.decrypt.Decrypt;
import com.nc.api.crypto.annotation.encrypt.Encrypt;
import com.nc.api.crypto.enums.CryptoType;
import com.nc.api.crypto.exception.DecryptErrorException;
import com.nc.api.crypto.exception.EncryptErrorException;
import com.nc.api.crypto.exception.SecretKeyEmptyException;
import com.nc.api.crypto.props.CryptoProperties;
import com.nc.core.common.utils.AesUtil;
import com.nc.core.common.utils.DesUtil;
import com.nc.core.common.utils.RsaUtil;
import com.nc.core.common.utils.StringUtil;

import java.util.Objects;

/**
 * @description: 加解密工具类
 * @author: jjxu
 * @time: 2022/2/10
 * @package: com.nc.api.crypto.utils
 */
public class CryptoUtil {

    public static String encryptData(CryptoProperties properties, byte[] jsonData, Encrypt encrypt) {
            CryptoType type = encrypt.value();
            String s = encrypt.secretKey();
        if (type == CryptoType.DES) {
                return DesUtil.encryptToBase64(jsonData, checkSecretKey(properties.getDesKey(),s,type.toString()));
            } else if (type == CryptoType.AES) {
                return AesUtil.encryptToBase64(jsonData, checkSecretKey(properties.getAesKey(),s,type.toString()));
            } else if (type == CryptoType.RSA) {
                return RsaUtil.encryptByPrivateKeyToBase64(checkSecretKey(properties.getRsaKey(),s,type.toString()), jsonData);
            } else {
                throw new EncryptErrorException("encrypt type not support");
            }

    }

    public static byte[] decryptData(CryptoProperties properties, byte[] bodyData, Decrypt decrypt) {
            CryptoType type = decrypt.value();
            String s = decrypt.secretKey();
            if (type == CryptoType.AES) {
                return AesUtil.decryptFormBase64(bodyData, checkSecretKey(properties.getAesKey(), s,type.toString()));
            } else if (type == CryptoType.DES) {
                return DesUtil.decryptFormBase64(bodyData, checkSecretKey(properties.getDesKey(), s,type.toString()));
            } else if (type == CryptoType.RSA) {
                return RsaUtil.decryptFromBase64(checkSecretKey(properties.getRsaKey(), s,type.toString()), bodyData);
            } else {
                throw new DecryptErrorException("decrypt type not support");
            }
    }
    /* *
     * @Author jjxu 比较key(用注解自带还是配置文件)
     * @Description
     * @Date 9:28 2022/2/10
     * @Param [k1, k2, keyName]
     **/
    private static String checkSecretKey(String k1,String k2,String cryptoType) {
        if (StringUtil.isBlank(k1) && StringUtil.isBlank(k2)) {
            throw new SecretKeyEmptyException(String.format("%s secret key is empty",cryptoType));
        } else {
            return StringUtil.isBlank(k1) ? k2 : k1;
        }
    }
}

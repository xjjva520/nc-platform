package com.nc.api.crypto.core;

import com.alibaba.fastjson.JSON;
import com.nc.api.crypto.annotation.encrypt.Encrypt;
import com.nc.api.crypto.props.CryptoProperties;
import com.nc.api.crypto.utils.CryptoUtil;
import com.nc.core.common.utils.ClassUtil;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/2/10
 * @package: com.nc.api.crypto.core
 */
@ControllerAdvice
@Order(1)
@ConditionalOnProperty(
        value = {"nc.crypto.enabled"},
        havingValue = "true",
        matchIfMissing = true
)
@EnableConfigurationProperties(CryptoProperties.class)
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {


    private final CryptoProperties properties;

    public EncryptResponseBodyAdvice(CryptoProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return ClassUtil.isAnnotated(returnType.getMethod(), Encrypt.class);
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return null;
        } else {
            response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
            Encrypt encrypt = (Encrypt)ClassUtil.getAnnotation(returnType.getMethod(), Encrypt.class);
            byte[] bytes = JSON.toJSONBytes(body);
            String s = CryptoUtil.encryptData(this.properties, bytes, encrypt);
            return s;
        }
    }
}

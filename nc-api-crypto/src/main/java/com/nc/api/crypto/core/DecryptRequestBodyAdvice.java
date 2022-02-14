package com.nc.api.crypto.core;

import com.nc.api.crypto.annotation.decrypt.Decrypt;
import com.nc.api.crypto.exception.DecryptErrorException;
import com.nc.api.crypto.props.CryptoProperties;
import com.nc.api.crypto.utils.CryptoUtil;
import com.nc.core.common.utils.ClassUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @description: RequestBodyAdvice 实现该接口，可以先对数据流信息处理
 * @author: jjxu
 * @time: 2022/2/9
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
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {

    private final CryptoProperties properties;

    public DecryptRequestBodyAdvice(CryptoProperties properties) {
        this.properties = properties;
    }

    /* *
     * @Author jjxu
     * @Description 看是否需要进行处理判断
     * @Date 16:50 2022/2/9
     * @Param [methodParameter, targetType, converterType]
     **/
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return ClassUtil.isAnnotated(methodParameter.getMethod(), Decrypt.class);
    }


    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        InputStream messageBody = inputMessage.getBody();
        if (messageBody.available() <= 0) {
            return inputMessage;
        } else {
            byte[] decryptedBody = null;
            //get annotation info
            Decrypt decrypt = (Decrypt)ClassUtil.getAnnotation(parameter.getMethod(), Decrypt.class);
            //流信息转换成byte
            byte[] bodyBytes = StreamUtils.copyToByteArray(messageBody);
            //正常报文放过 并且配置是true
            if (bodyBytes.length > 0 && bodyBytes[0] == "{".getBytes()[0] && this.properties.getSkipJson()) {
                decryptedBody = bodyBytes;
            } else {
                decryptedBody = CryptoUtil.decryptData(this.properties, bodyBytes, decrypt);
            }

            if (decryptedBody == null) {
                throw new DecryptErrorException("Decrypt error, please check encrypt is ok");
            } else {
                InputStream inputStream = new ByteArrayInputStream(decryptedBody);
                return new DecryptHttpInputMessage(inputStream, inputMessage.getHeaders());
            }
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}

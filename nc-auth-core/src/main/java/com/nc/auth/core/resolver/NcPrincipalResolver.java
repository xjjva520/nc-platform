package com.nc.auth.core.resolver;

import com.nc.auth.core.entity.NcPrincipal;
import com.nc.auth.core.utils.NcAuthUtil;
import com.nc.auth.core.utils.TokenUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @description: NCPrincipal 对象请求自动映射
 * @author: jjxu
 * @time: 2022/1/24
 * @package: com.nc.auth.core.resolver
 */
public class NcPrincipalResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(NcPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return NcAuthUtil.getAccount();
    }
}

package com.nc.auth.core.config;

import com.nc.auth.core.resolver.NcPrincipalResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/28
 * @package: com.nc.auth.core.config
 */
@Configuration
@Order(-2147483648)
public class NcWebMvcConfiguration implements WebMvcConfigurer {

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new NcPrincipalResolver());
    }
}

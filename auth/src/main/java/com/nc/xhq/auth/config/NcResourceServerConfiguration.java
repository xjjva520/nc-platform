package com.nc.xhq.auth.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @description: 为什么要写这个，可以看AbstractAuthenticationProcessingFilter
 * @author: jjxu
 * @time: 2022/1/27
 * @package: com.nc.xhq.auth.config
 */
@Configuration
public class NcResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();

        //如果是表单登录login，可以定义成功之后处理，或者失败后处理
        http.formLogin()
                .and()
                .authorizeRequests()
                .antMatchers(  //哪些url不进行验证
                        "/actuator/**",
                        "/oauth/**",
                        "/t/**"
                        ).permitAll()
                .anyRequest().authenticated().and()
                .csrf()
                .disable();
    }
}

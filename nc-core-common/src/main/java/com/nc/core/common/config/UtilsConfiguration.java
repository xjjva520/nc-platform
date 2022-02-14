package com.nc.core.common.config;

import com.nc.core.common.utils.SpringApplicationUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/20
 * @package: com.nc.auth.core.config
 */
@Configuration
public class UtilsConfiguration {

    @Bean
    public SpringApplicationUtil springUtil() {
        return new SpringApplicationUtil();
    }

}

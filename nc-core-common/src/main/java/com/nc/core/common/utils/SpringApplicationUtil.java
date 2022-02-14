package com.nc.core.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @description: spring 容器获取bean实体方法
 * @author: jjxu
 * @time: 2022/1/19
 * @package: com.nc.auth.core.utils
 */
public class SpringApplicationUtil implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SpringApplicationUtil.class);
    private static ApplicationContext context;

    public SpringApplicationUtil() {
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringApplicationUtil.context = context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return clazz == null ? null : context.getBean(clazz);
    }

    public static Object getBean(String beanId) {
        return beanId == null ? null : context.getBean(beanId);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (null != beanName && !"".equals(beanName.trim())) {
            return clazz == null ? null : context.getBean(beanName, clazz);
        } else {
            return null;
        }
    }

    public static ApplicationContext getContext() {
        return context == null ? null : context;
    }

    public static void publishEvent(ApplicationEvent event) {
        if (context != null) {
            try {
                context.publishEvent(event);
            } catch (Exception var2) {
                log.error(var2.getMessage());
            }

        }
    }
}

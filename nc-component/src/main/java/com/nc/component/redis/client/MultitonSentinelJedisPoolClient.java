package com.nc.component.redis.client;

import com.nc.component.redis.factory.MultitonJedisPoolBeanFactory;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/25
 * @package: com.nc.component.redis.service
 */
public class MultitonSentinelJedisPoolClient {

    private MultitonJedisPoolBeanFactory poolBeanFactory;

    public MultitonSentinelJedisPoolClient(MultitonJedisPoolBeanFactory poolBeanFactory) {
        this.poolBeanFactory = poolBeanFactory;
    }


}

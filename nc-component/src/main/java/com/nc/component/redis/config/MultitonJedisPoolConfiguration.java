package com.nc.component.redis.config;

import com.nc.component.redis.client.MultitonSentinelJedisPoolClient;
import com.nc.component.redis.factory.MultitonJedisPoolBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * @description: sentinel 支持多mastername hash分片计算
 * 需要进行配置，因为只支持jedis连接池，只有当redis配置为needMultiton && client-type 为jedis
 * @author: jjxu
 * @time: 2022/1/20
 * @package: com.nc.component.config
 */
@Component
@EnableConfigurationProperties({RedisProperties.class})
@ConditionalOnProperty(prefix ="spring.redis" ,name = {"needMultiton","client-type"}, havingValue ="jedis", matchIfMissing = false)
public class MultitonJedisPoolConfiguration {


    @Bean
    public MultitonJedisPoolBeanFactory multitonJedisPoolBeanFactory(RedisProperties ncRedisProperties,
                                                                     @Autowired ApplicationContext applicationContext){
        MultitonJedisPoolBeanFactory factory = new MultitonJedisPoolBeanFactory(ncRedisProperties,applicationContext);
        return factory;
    }

    @Bean
    public MultitonSentinelJedisPoolClient multitonSentinelJedisPoolClient(MultitonJedisPoolBeanFactory beanFactory){
        return new MultitonSentinelJedisPoolClient(beanFactory);
    }

}

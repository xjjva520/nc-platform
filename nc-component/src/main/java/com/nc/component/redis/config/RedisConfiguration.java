package com.nc.component.redis.config;

import com.nc.component.redis.client.RedisOprClient;
import com.nc.component.redis.props.NcRedisProperties;
import com.nc.component.redis.serializer.ProtoStuffSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/25
 * @package: com.nc.component.config
 */
@EnableCaching
@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
@EnableConfigurationProperties(NcRedisProperties.class)
public class RedisConfiguration {


    @Bean
    @ConditionalOnMissingBean({RedisSerializer.class})
    public RedisSerializer<Object> redisSerializer(NcRedisProperties properties) {
        NcRedisProperties.SerializerType serializerType = properties.getSerializerType()==null? NcRedisProperties.SerializerType.JDK:properties.getSerializerType();
        switch (serializerType){
            case JDK:
                ClassLoader classLoader = this.getClass().getClassLoader();
                return new JdkSerializationRedisSerializer(classLoader);
            case JSON:
                return new GenericJackson2JsonRedisSerializer();
            case ProtoStuff:
                return new ProtoStuffSerializer();
        }
        return new ProtoStuffSerializer();
    }

    @Bean(name = {"redisTemplate"})
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer<Object> redisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean
    public RedisOprClient redisClient(RedisTemplate<String,Object> redisTemplate, StringRedisTemplate stringRedisTemplate){
        return new RedisOprClient(redisTemplate,stringRedisTemplate);
    }
}

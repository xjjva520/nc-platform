package com.nc.component.redis.client;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/25
 * @package: com.nc.component.service
 */
public class RedisOprClient {

    private RedisTemplate<String,Object> redisTemplate;

    private StringRedisTemplate stringRedisTemplate;

    public RedisOprClient(RedisTemplate<String, Object> StringKeyTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = StringKeyTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    public String getStr(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public Object getObj(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void setStr(String key, String val, long timeOut, TimeUnit timeUnit){
         stringRedisTemplate.opsForValue().set(key,val,timeOut,timeUnit);
    }

    public void setObj(String key, Object val, long timeOut, TimeUnit timeUnit){
         redisTemplate.opsForValue().set(key,val,timeOut,timeUnit);
    }

    public Long incrBy(String key, long longValue) {
        return redisTemplate.opsForValue().increment(key, longValue);
    }

    public void deleteKey(String key){
        redisTemplate.delete(key);
    }

    public void expire(String key ,long expire,TimeUnit unit){
        redisTemplate.expire(key,expire,unit);
    }
}

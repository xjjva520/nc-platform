package com.nc.component.redis.factory;

import com.nc.core.common.utils.SpringApplicationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/21
 * @package: com.nc.component.factory
 */
public class MultitonJedisPoolBeanFactory {

    private final static Logger logger = LogManager.getLogger(MultitonJedisPoolBeanFactory.class);

    private static final String beanPrefix = "jedisSentinelPool";

    private RedisProperties ncRedisProperties;

    private ApplicationContext applicationContext;

    public MultitonJedisPoolBeanFactory(RedisProperties ncRedisProperties, ApplicationContext context) {
        this.ncRedisProperties = ncRedisProperties;
        this.applicationContext = context;
    }

    @PostConstruct
    public void dynamicCreateRedisPool() throws Exception {
        try {
            Assert.notNull(this.ncRedisProperties,()->"this redis properties is null");
            Assert.notNull(this.ncRedisProperties,()->"this applicationContext is null");
            ConfigurableApplicationContext context = (ConfigurableApplicationContext)applicationContext;
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)context.getBeanFactory();

            //获取配置文件
            JedisPoolConfig jedisPoolConfig = this.initJedisPoolConfig(ncRedisProperties);

            Duration connectTimeout = ncRedisProperties.getConnectTimeout();
            Duration timeout = ncRedisProperties.getTimeout();
            final String password = ncRedisProperties.getPassword();
            //sentinels 池
            Set<String> sentinelNodes = ncRedisProperties.getSentinel().getNodes().stream().collect(Collectors.toSet());
            String masterName = ncRedisProperties.getSentinel().getMaster();

            //监听的 master数组
            String[] masterNames = masterName.split(",");
            for (int i=0;i<masterNames.length;i++){
                //构造 JedisSeninelPool
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(JedisSentinelPool.class);
                beanDefinitionBuilder.addConstructorArgValue(masterNames[i]);
                beanDefinitionBuilder.addConstructorArgValue(sentinelNodes);
                beanDefinitionBuilder.addConstructorArgValue(jedisPoolConfig);
                beanDefinitionBuilder.addConstructorArgValue(connectTimeout.toMillis());
                beanDefinitionBuilder.addConstructorArgValue(timeout.toMillis());
                beanDefinitionBuilder.addConstructorArgValue(password);
                beanDefinitionBuilder.addConstructorArgValue(0);
                //注册到spring容器中
                beanFactory.registerBeanDefinition(beanPrefix + i,  beanDefinitionBuilder.getBeanDefinition());
            }
            logger.info("jedisSentinelPools init is ok:");
        }catch (Exception e) {
            logger.error("jedisSentinelPools init occur error:",e);
        }

    }

    /**
     * 通过key进行hash算法，取模进行redis分片
     * @param key
     * @return
     */
    public JedisSentinelPool getSentinelPool(String key) {
        String masterName = ncRedisProperties.getSentinel().getMaster();
        //监听的 master数组
        String[] masterNames = masterName.split(",");
        int partition = key.hashCode()%masterNames.length;
        JedisSentinelPool pool = (JedisSentinelPool) SpringApplicationUtil.getBean(beanPrefix+partition);
        logger.info("get JedisSentinelPool by key = {},partition ={},pool = {}",key,partition,pool.getCurrentHostMaster());
        return pool;
    }

    private JedisPoolConfig initJedisPoolConfig(RedisProperties redisProperties) {
        RedisProperties.Pool pool = redisProperties.getJedis().getPool();
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        if (pool.getTimeBetweenEvictionRuns() != null) {
            config.setTimeBetweenEvictionRuns(pool.getTimeBetweenEvictionRuns());
        }
        if (pool.getMaxWait() != null) {
            config.setMaxWait(pool.getMaxWait());
        }

        return config;
    }

}

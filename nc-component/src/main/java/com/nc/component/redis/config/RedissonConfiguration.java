package com.nc.component.redis.config;

import com.nc.component.redis.client.RedisLockClient;
import com.nc.component.redis.client.RedisLockClientImpl;
import com.nc.component.redis.props.NcRedisProperties;
import com.nc.core.common.utils.StringUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @description: 在池的配置中 一般maxActive最大激活连接数
 * @author: jjxu
 * @time: 2022/1/22
 * @package: com.nc.component.config
 */
@Configuration
@ConditionalOnClass({RedissonClient.class})
@EnableConfigurationProperties({NcRedisProperties.class})
public class RedissonConfiguration {

    private final String nodeFormat = "redis://%s";

    @Bean
    @ConditionalOnMissingBean
    public RedissonClient redissonClient(NcRedisProperties ncRedisProperties) {
        NcRedisProperties.Mode mode = ncRedisProperties.getReddison().getMode();
        Config config;
        switch(mode) {
            case sentinel:
                config = sentinelConfig(ncRedisProperties);
                break;
            case cluster:
                config = clusterConfig(ncRedisProperties);
                break;
            case master:
                config = masterSlaveConfig(ncRedisProperties);
                break;
            default:
                config = singleConfig(ncRedisProperties);
        }
        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            value = {"spring.redis.reddison.enableLock"},
            havingValue = "true"
    )
    public RedisLockClient redisLockClient(RedissonClient redissonClient) {
        return new RedisLockClientImpl(redissonClient);
    }


    private Config singleConfig(NcRedisProperties ncRedisProperties) {
        Config config = new Config();
        SingleServerConfig serversConfig = config.useSingleServer();
        RedisProperties properties = ncRedisProperties.getRedisProperties();
        //address=ip:port
        String address = String.format(nodeFormat,properties.getHost()+":"+properties.getPort());
        serversConfig.setAddress(address);
        String password = properties.getPassword();
        if (StringUtil.isNotBlank(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setDatabase(properties.getDatabase());
        this.applyPool(serversConfig,ncRedisProperties);
        return config;
    }

    private  Config masterSlaveConfig(NcRedisProperties ncRedisProperties) {
        Config config = new Config();
        MasterSlaveServersConfig serversConfig = config.useMasterSlaveServers();
        RedisProperties properties = ncRedisProperties.getRedisProperties();
        NcRedisProperties.MasterSlave masterSlave = ncRedisProperties.getMasterSlave();
        String master = String.format(nodeFormat, masterSlave.getMaster());
        serversConfig.setMasterAddress(master);
        List<String> nodes = masterSlave.getSlaveNodes();
        serversConfig.addSlaveAddress(nodes.stream().map(s -> String.format(nodeFormat, s)).toArray(String[]::new));
        String password = properties.getPassword();
        if (StringUtil.isNotBlank(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setDatabase(properties.getDatabase());
        this.applyPool(serversConfig,ncRedisProperties);
        return config;
    }

    private  Config sentinelConfig(NcRedisProperties ncRedisProperties) {
        RedisProperties properties = ncRedisProperties.getRedisProperties();
        RedisProperties.Sentinel sentinel = properties.getSentinel();
        List<String> nodes = sentinel.getNodes();
        Config config = new Config();
        SentinelServersConfig serversConfig = config.useSentinelServers();
        serversConfig.setMasterName(sentinel.getMaster());
        serversConfig.addSentinelAddress(nodes.stream().map(s -> String.format(nodeFormat, s)).toArray(String[]::new));
        String password = sentinel.getPassword();
        if (StringUtil.isNotBlank(password)) {
            serversConfig.setPassword(password);
        }
        serversConfig.setDatabase(properties.getDatabase());
        this.applyPool(serversConfig,ncRedisProperties);
        return config;
    }

    private Config clusterConfig(NcRedisProperties ncRedisProperties) {
        Config config = new Config();
        ClusterServersConfig serversConfig = config.useClusterServers();
        RedisProperties properties = ncRedisProperties.getRedisProperties();
        List<String> nodes = properties.getCluster().getNodes();
        serversConfig.addNodeAddress(nodes.stream().map(s -> String.format(nodeFormat, s)).toArray(String[]::new));
        String password = properties.getPassword();
        if (StringUtil.isNotBlank(password)) {
            serversConfig.setPassword(password);
        }
        this.applyPool(serversConfig,ncRedisProperties);
        return config;
    }


    private void applyPool(BaseConfig config, NcRedisProperties redisProperties){
        RedisProperties.Pool pool = redisProperties.getReddison().getPool();
        RedisProperties properties = redisProperties.getRedisProperties();
        if(config instanceof BaseMasterSlaveServersConfig){
            BaseMasterSlaveServersConfig serversConfig = (BaseMasterSlaveServersConfig)config;
            serversConfig.setMasterConnectionPoolSize(pool.getMaxActive());
            serversConfig.setMasterConnectionMinimumIdleSize(pool.getMinIdle());
            serversConfig.setSlaveConnectionPoolSize(pool.getMaxActive());
            serversConfig.setSlaveConnectionMinimumIdleSize(pool.getMinIdle());
            serversConfig.setIdleConnectionTimeout((int) properties.getConnectTimeout().toMillis());
            serversConfig.setConnectTimeout((int) properties.getConnectTimeout().toMillis());
            serversConfig.setTimeout((int) properties.getTimeout().toMillis());
            return;
        }
        if(config instanceof SingleServerConfig){
            SingleServerConfig serversConfig = (SingleServerConfig)config;
            serversConfig.setConnectionPoolSize(pool.getMaxActive());
            serversConfig.setConnectionMinimumIdleSize(pool.getMinIdle());
            serversConfig.setIdleConnectionTimeout((int) properties.getConnectTimeout().toMillis());
            serversConfig.setConnectTimeout((int) properties.getConnectTimeout().toMillis());
            serversConfig.setTimeout((int) properties.getTimeout().toMillis());
            return;
        }
        throw new RuntimeException("unsupport instance of not BaseConfig");
    }

}

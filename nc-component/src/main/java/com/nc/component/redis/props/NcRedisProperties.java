package com.nc.component.redis.props;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/22
 * @package: com.nc.component.props
 */
@ConfigurationProperties(prefix = "spring.redis")
@EnableConfigurationProperties({RedisProperties.class})
public class NcRedisProperties{

    private NcRedisProperties.SerializerType serializerType;

    private RedisProperties redisProperties;
    //分布式锁配置
    private NcRedisProperties.Reddison reddison;

    private NcRedisProperties.MasterSlave masterSlave;

    //是否启用sentinel 模式下多连接池模式
    private String needMultiton;

    public NcRedisProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    public Reddison getReddison() {
        return reddison;
    }

    public void setReddison(Reddison reddison) {
        this.reddison = reddison;
    }

    public NcRedisProperties.MasterSlave getMasterSlave() {
        return masterSlave;
    }

    public void setMasterSlave(NcRedisProperties.MasterSlave masterSlave) {
        this.masterSlave = masterSlave;
    }

    public RedisProperties getRedisProperties() {
        return redisProperties;
    }

    public void setRedisProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    public SerializerType getSerializerType() {
        return serializerType;
    }

    public void setSerializerType(SerializerType serializerType) {
        this.serializerType = serializerType;
    }

    public String getNeedMultiton() {
        return needMultiton;
    }

    public void setNeedMultiton(String needMultiton) {
        this.needMultiton = needMultiton;
    }

    public static class Reddison{
        private final RedisProperties.Pool pool = new RedisProperties.Pool();
        private NcRedisProperties.Mode mode = Mode.single;
        private Boolean enableLock = Boolean.FALSE;

        public Reddison() {

        }
        public RedisProperties.Pool getPool() {
            return pool;
        }

        public Boolean getEnableLock() {
            return enableLock;
        }

        public void setEnableLock(Boolean enableLock) {
            this.enableLock = enableLock;
        }

        public Mode getMode() {
            return mode;
        }

        public void setMode(Mode mode) {
            this.mode = mode;
        }
    }

    public static class MasterSlave{
        private String master;

        private List<String> slaveNodes;

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }

        public List<String> getSlaveNodes() {
            return slaveNodes;
        }

        public void setSlaveNodes(List<String> slaveNodes) {
            this.slaveNodes = slaveNodes;
        }
    }



    public static enum Mode {
        single,
        master,
        sentinel,
        cluster;

        private Mode() {
        }
    }

    public static enum SerializerType {
        ProtoStuff,
        JSON,
        JDK;

        private SerializerType() {
        }
    }

}

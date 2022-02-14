package com.nc.core.common.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/20
 * @package: com.nc.auth.core.props
 */
@ConfigurationProperties(prefix = "nc.thread.pool")
public class ThreadPoolProperties {

    //核心线程数
    private  Integer corePoolSize = 8;

    //最大线程数
    private  Integer maximumPoolSize = 16;

    //活跃时长（单位s）
    private  Long keepAliveTime = 60L;

    //有界队列长度
    private  Integer capacity = 32;

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}

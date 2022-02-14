package com.nc.component.redis.enums;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/24
 * @package: com.nc.component.enums
 */
public enum LockType {

    REENTRANT,
    FAIR;

    private LockType() {
    }
}

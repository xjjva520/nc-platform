package com.nc.component.redis.serializer;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/25
 * @package: com.nc.component.redis.serializer
 */
public class BytesWrapper<T> implements Cloneable{

    private T value;

    public BytesWrapper() {
    }

    public BytesWrapper(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public BytesWrapper<T> clone() {
        try {
            return (BytesWrapper)super.clone();
        } catch (CloneNotSupportedException var2) {
            return new BytesWrapper();
        }
    }
}

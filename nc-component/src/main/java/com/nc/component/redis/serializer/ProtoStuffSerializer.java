package com.nc.component.redis.serializer;

import com.nc.core.common.utils.ObjectUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/25
 * @package: com.nc.component.redis.serializer
 */
public class ProtoStuffSerializer implements RedisSerializer<Object> {

    private final Schema<BytesWrapper> schema = RuntimeSchema.getSchema(BytesWrapper.class);

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        if (object == null) {
            return null;
        } else {
            LinkedBuffer buffer = LinkedBuffer.allocate(512);

            byte[] var3;
            try {
                var3 = ProtostuffIOUtil.toByteArray(new BytesWrapper(object), this.schema, buffer);
            } finally {
                buffer.clear();
            }

            return var3;
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (ObjectUtil.isEmpty(bytes)) {
            return null;
        } else {
            BytesWrapper<Object> wrapper = new BytesWrapper();
            ProtostuffIOUtil.mergeFrom(bytes, wrapper, this.schema);
            return wrapper.getValue();
        }
    }
}

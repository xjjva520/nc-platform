package com.nc.core.common.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/19
 * @package: com.nc.auth.core.utils
 */
public class CollectionUtil extends CollectionUtils {

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static <T> boolean contains(@Nullable T[] array, final T element) {
        return array != null && Arrays.stream(array).anyMatch((x) -> {
            return ObjectUtil.nullSafeEquals(x, element);
        });
    }


    public static boolean isArray(Object obj) {
        return null != obj && obj.getClass().isArray();
    }

    @SafeVarargs
    public static <E> Set<E> ofImmutableSet(E... es) {
        Objects.requireNonNull(es, "args es is null.");
        return (Set)Arrays.stream(es).collect(Collectors.toSet());
    }

    @SafeVarargs
    public static <E> List<E> ofImmutableList(E... es) {
        Objects.requireNonNull(es, "args es is null.");
        return (List)Arrays.stream(es).collect(Collectors.toList());
    }

    public static <E> List<E> toList(Iterable<E> elements) {
        Objects.requireNonNull(elements, "elements es is null.");
        if (elements instanceof Collection) {
            return new ArrayList((Collection)elements);
        } else {
            Iterator<E> iterator = elements.iterator();
            ArrayList list = new ArrayList();

            while(iterator.hasNext()) {
                list.add(iterator.next());
            }

            return list;
        }
    }
}

package com.nc.core.common.utils;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/19
 * @package: com.nc.auth.core.utils
 */
public class ExceptionsUtil {

    public static RuntimeException unchecked(Throwable e) {
        if (e instanceof Error) {
            throw (Error)e;
        } else if (!(e instanceof IllegalAccessException) && !(e instanceof IllegalArgumentException) && !(e instanceof NoSuchMethodException)) {
            if (e instanceof InvocationTargetException) {
                return new RuntimeException(((InvocationTargetException)e).getTargetException());
            } else if (e instanceof RuntimeException) {
                return (RuntimeException)e;
            } else {
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }

                return (RuntimeException) e;
            }
        } else {
            return new IllegalArgumentException(e);
        }
    }


    public static Throwable unwrap(Throwable wrapped) {
        Throwable unwrapped = wrapped;

        while(true) {
            while(!(unwrapped instanceof InvocationTargetException)) {
                if (!(unwrapped instanceof UndeclaredThrowableException)) {
                    return unwrapped;
                }

                unwrapped = ((UndeclaredThrowableException)unwrapped).getUndeclaredThrowable();
            }

            unwrapped = ((InvocationTargetException)unwrapped).getTargetException();
        }
    }
}

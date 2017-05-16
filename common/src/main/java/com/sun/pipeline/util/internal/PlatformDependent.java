package com.sun.pipeline.util.internal;

import com.sun.pipeline.util.internal.logging.InternalLogger;
import com.sun.pipeline.util.internal.logging.InternalLoggerFactory;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

import static com.sun.pipeline.util.internal.ObjectUtil.checkNotNull;

/**
 * Created by zksun on 15/05/2017.
 */
public class PlatformDependent {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PlatformDependent.class);
    static final Unsafe UNSAFE;
    private static final long ADDRESS_FIELD_OFFSET;

    static {
        ByteBuffer direct = ByteBuffer.allocateDirect(1);
        Field addressField;

        try {
            addressField = Buffer.class.getDeclaredField("address");
            addressField.setAccessible(true);
        } catch (Throwable e) {
            addressField = null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("java.nio.Buffer.address: {}", null != addressField ? "available" : "unavailable");
        }

        Unsafe unsafe;
        if (null != addressField) {

            try {
                Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                unsafe = (Unsafe) unsafeField.get(null);

                if (logger.isDebugEnabled()) {
                    logger.debug("sun.misc.Unsafe.theUnsafe: {}", unsafe != null ? "available" : "unavailable");
                }

                try {
                    if (null != unsafe) {
                        unsafe.getClass().getDeclaredMethod("copyMemory", Object.class, long.class, Object.class, long.class, long.class);
                        if (logger.isDebugEnabled()) {
                            logger.debug("sun.misc.Unsafe.copyMemory: available");
                        }
                    }
                } catch (NoSuchMethodException e) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("sun.misc.Unsafe.copyMemory: unavailable");
                    }
                    throw e;
                } catch (SecurityException e) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("sun.misc.Unsafe.copyMemory: unavailable");
                        throw e;
                    }
                }
            } catch (Throwable e) {
                unsafe = null;
            }

        } else {
            unsafe = null;
        }

        UNSAFE = unsafe;

        if (null == unsafe) {
            ADDRESS_FIELD_OFFSET = -1;
        } else {
            ADDRESS_FIELD_OFFSET = objectFieldOffset(addressField);
        }
    }

    static void throwException(Throwable cause) {
        UNSAFE.throwException(checkNotNull(cause, "cause"));
    }

    static boolean hasUnsafe() {
        return null != UNSAFE;
    }

    static long directBufferAddress(ByteBuffer buffer) {
        return getLong(buffer, ADDRESS_FIELD_OFFSET);
    }


    private static long getLong(Object object, long fieldOffset) {
        return UNSAFE.getLong(object, fieldOffset);
    }

    static long objectFieldOffset(Field field) {
        return UNSAFE.objectFieldOffset(field);
    }

    static ClassLoader getClassLoader(final Class<?> clazz) {
        if (null == System.getSecurityManager()) {
            return clazz.getClassLoader();
        } else {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
                @Override
                public ClassLoader run() {
                    return clazz.getClassLoader();
                }
            });
        }
    }

    static ClassLoader getContextClassLoader() {
        if (null == System.getSecurityManager()) {
            return Thread.currentThread().getContextClassLoader();
        } else {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
                @Override
                public ClassLoader run() {
                    return ClassLoader.getSystemClassLoader();
                }
            });
        }
    }

    static ClassLoader getSystemClassLoader() {
        if (null == System.getSecurityManager()) {
            return ClassLoader.getSystemClassLoader();
        } else {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
                @Override
                public ClassLoader run() {
                    return ClassLoader.getSystemClassLoader();
                }
            });
        }
    }

    static int addressSize() {
        return UNSAFE.addressSize();
    }

    private PlatformDependent() {
    }
}

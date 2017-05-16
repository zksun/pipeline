package com.sun.pipeline.util.internal;

import com.sun.pipeline.util.internal.logging.InternalLogger;
import com.sun.pipeline.util.internal.logging.InternalLoggerFactory;

import java.nio.ByteBuffer;
import java.util.regex.Pattern;

/**
 * Created by zksun on 15/05/2017.
 */
public class Platform {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Platform.class);

    private static final Pattern MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN = Pattern.compile(
            "\\s*-XX:MaxDirectMemorySize\\s*=\\s*([0-9]+)\\s*([kKmMgG]?)\\s*$");


    private static final boolean HAS_UNSAFE = _hasUnsafe();

    public static void throwException(Throwable t) {
        if (_hasUnsafe()) {
            PlatformDependent.throwException(t);
        } else {
            Platform.<RuntimeException>_throwException(t);
        }
    }

    private static <E extends Throwable> void _throwException(Throwable t) throws E {
        throw (E) t;
    }

    public static boolean hasUnsafe() {
        return HAS_UNSAFE;
    }

    private static boolean _hasUnsafe() {
        boolean noUnsafe = SystemPropertyUtil.getBoolean("com.sun.pipeline.noUnsafe", false);
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.noUnsafe: {}", noUnsafe);
        }

        if (noUnsafe) {
            if (logger.isDebugEnabled()) {
                logger.debug("sun.misc.Unsafe: unavailable (io.netty.noUnsafe)");
            }
            return false;
        }

        try {
            boolean hasUnsafe = PlatformDependent.hasUnsafe();
            logger.debug("sun.misc.Unsafe: {}", hasUnsafe ? "available" : "unavailable");
            return hasUnsafe;
        } catch (Throwable t) {
            // Probably failed to initialize PlatformDependent0.
            return false;
        }
    }

    public static long directBufferAddress(ByteBuffer buffer) {
        return PlatformDependent.directBufferAddress(buffer);
    }

}

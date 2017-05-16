package com.sun.pipeline.util;

import com.sun.pipeline.util.internal.Platform;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zksun on 15/05/2017.
 */
public class AbstractConstant<T extends AbstractConstant<T>> implements Constant<T> {

    private final int id;
    private final String name;
    private volatile long uniquifier;
    private ByteBuffer directBuffer;

    protected AbstractConstant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public final int id() {
        return id;
    }

    public final String name() {
        return name;
    }

    public final int compareTo(T o) {
        if (this == o) {
            return 0;
        }

        AbstractConstant<T> other = o;
        int returnCode;

        returnCode = hashCode() - other.hashCode();
        if (returnCode != 0) {
            return returnCode;
        }

        long thisUV = uniquifier();
        long otherUV = other.uniquifier();

        if (thisUV < otherUV) {
            return -1;
        }

        if (thisUV > otherUV) {
            return 1;
        }

        throw new Error("failed to compare two different constants");
    }

    private long uniquifier() {
        long uniquifier;
        if ((uniquifier = this.uniquifier) == 0) {
            while ((uniquifier = this.uniquifier) == 0) {
                if (Platform.hasUnsafe()) {
                    directBuffer = ByteBuffer.allocateDirect(1);
                    this.uniquifier = Platform.directBufferAddress(directBuffer);
                } else {
                    directBuffer = null;
                    this.uniquifier = ThreadLocalRandom.current().nextLong();
                }
            }
        }

        return uniquifier;
    }

    @Override
    public String toString() {
        return name();
    }
}

package com.sun.pipeline.util.calculate;

import java.io.File;
import java.io.InputStream;

/**
 * Created by zksun on 08/06/2017.
 */
public interface DataFactor<D> extends Factor {

    boolean fill(InputStream inputStream);

    boolean fill(File file);

    boolean fill(String json);

    D getData();

    boolean hasFull();
}

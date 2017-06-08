package com.sun.pipeline.util.internal.io;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zksun on 08/06/2017.
 */
public interface Container<D, I> {
    D getData();

    Container swallow(Container container);

    Container swallow(InputStream inputStream);

    Container swallow(File file);

    Container swallow(String s);

    Container swallow(I input);

    Container swallow(OutputStream outputStream);


}

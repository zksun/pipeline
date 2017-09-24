package com.sun.pipeline.util.props;

/**
 * Created by zhikunsun on 17/9/24.
 */
public interface Props {
    <T> T getProp(String key, T defaultValue);
}

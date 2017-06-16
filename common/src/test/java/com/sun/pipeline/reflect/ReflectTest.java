package com.sun.pipeline.reflect;

import com.sun.pipeline.util.TypeParameterTypeFinder;
import org.junit.Test;

/**
 * Created by zksun on 16/06/2017.
 */
public class ReflectTest {
    @Test
    public void typeParameterTypeFinderTest() {
        TestClass stringIntegerTestClass = new TestClass();
        Class<?> i = TypeParameterTypeFinder.getTypeMatcherFinder().find(stringIntegerTestClass, SuperClass.class, "D");
        System.out.println(i.getName());
    }
}

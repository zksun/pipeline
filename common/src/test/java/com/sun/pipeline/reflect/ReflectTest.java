package com.sun.pipeline.reflect;

import com.sun.pipeline.util.TypeParameterTypeFinder;
import com.sun.pipeline.util.internal.io.JsonContainer;
import org.junit.Test;

/**
 * Created by zksun on 16/06/2017.
 */
public class ReflectTest {
    @Test
    public void typeParameterTypeFinderTest() {
        TestClass stringIntegerTestClass = new TestClass();
        Class<?> i = TypeParameterTypeFinder.getTypeMatcherFinder().find(stringIntegerTestClass, SuperClass.class, "L");
        System.out.println(i.getName());
    }

    @Test
    public void typeParameterTypeFinderJavaBeanTest() {
        TestJsonContainer testJsonContainer = new TestJsonContainer();
        Class<?> i = TypeParameterTypeFinder.getTypeMatcherFinder().find(testJsonContainer, JsonContainer.class, "D");
        System.out.println(i.getName());
    }
}

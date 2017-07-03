package com.sun.pipeline.util.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by zksun on 13/06/2017.
 */
public final class TypeParameterTypeFinder {

    private static Boolean ACTIVE = false;

    private Map<Class<?>, Class<?>> typeParameterTypeGetCache;

    private Map<Class<?>, Map<String, Class<?>>> typeParameterTypeFindCache;

    private Map<Class<?>, Class<?>> typeParameterTypeGetCache() {
        Map<Class<?>, Class<?>> cache = typeParameterTypeGetCache;
        synchronized (this) {
            if (null == cache) {
                typeParameterTypeGetCache = cache = new IdentityHashMap<>();
            }
        }
        return cache;
    }

    private Map<Class<?>, Map<String, Class<?>>> getTypeParameterTypeFindCache() {
        Map<Class<?>, Map<String, Class<?>>> cache = typeParameterTypeFindCache;
        synchronized (this) {
            if (null == cache) {
                typeParameterTypeFindCache = cache = new IdentityHashMap<>();
            }
        }
        return cache;
    }

    public static TypeParameterTypeFinder getTypeMatcherFinder() {
        if (ACTIVE) {
            return slowTypeParameterTypeFinder;
        }
        synchronized (ACTIVE) {
            if (null == slowTypeParameterTypeFinder) {
                slowTypeParameterTypeFinder = new TypeParameterTypeFinder();
                ACTIVE = true;
            }
        }
        return slowTypeParameterTypeFinder;
    }

    private Class<?> get(final Class<?> parameterType) {
        final Map<Class<?>, Class<?>> getCache = typeParameterTypeGetCache();
        Class<?> matcher = getCache.get(parameterType);
        if (null == matcher) {
            if (parameterType == Object.class) {
                matcher = Object.class;
            }

            if (null == matcher) {
                matcher = parameterType;
            }

            getCache.put(parameterType, parameterType);
        }

        return matcher;
    }

    public Class<?> find(final Object object, final Class<?> parameterizedSuperclass, final String typeParamName) {
        final Map<Class<?>, Map<String, Class<?>>> findCache = getTypeParameterTypeFindCache();
        final Class<?> thisClass = object.getClass();

        Map<String, Class<?>> map = findCache.get(thisClass);
        if (null == map) {
            map = new HashMap<>();
            findCache.put(thisClass, map);
        }

        Class<?> matcher = map.get(typeParamName);
        if (null == matcher) {
            matcher = get(find0(object, parameterizedSuperclass, typeParamName));
            map.put(typeParamName, matcher);
        }

        return matcher;
    }

    private Class<?> find0(final Object object, Class<?> parameterizedSuperclass, String typeParamName) {
        final Class<?> thisClass = object.getClass();
        Class<?> currentClass = thisClass;
        for (; ; ) {
            if (currentClass.getSuperclass() == parameterizedSuperclass) {
                int typeParamIndex = -1;
                TypeVariable<?>[] typeParams = currentClass.getSuperclass().getTypeParameters();
                for (int i = 0; i < typeParams.length; i++) {
                    if (typeParamName.equals(typeParams[i].getName())) {
                        typeParamIndex = i;
                        break;
                    }
                }

                if (typeParamIndex < 0) {
                    throw new IllegalStateException("unknown type parameter '" + typeParamName + ":" + parameterizedSuperclass);
                }

                Type genericSuperType = currentClass.getGenericSuperclass();
                if (!(genericSuperType instanceof ParameterizedType)) {
                    return Object.class;
                }

                Type[] actualTypeParams = ((ParameterizedType) genericSuperType).getActualTypeArguments();

                Type actualTypeParam = actualTypeParams[typeParamIndex];
                if (actualTypeParam instanceof ParameterizedType) {
                    actualTypeParam = ((ParameterizedType) actualTypeParam).getRawType();
                }

                if (actualTypeParam instanceof Class) {
                    return (Class<?>) actualTypeParam;
                }

                if (actualTypeParam instanceof GenericArrayType) {
                    Type componentType = ((GenericArrayType) actualTypeParam).getGenericComponentType();
                    if (componentType instanceof ParameterizedType) {
                        componentType = ((ParameterizedType) componentType).getRawType();
                    }
                    if (componentType instanceof Class) {
                        return Array.newInstance((Class<?>) componentType, 0).getClass();
                    }
                }
                if (actualTypeParam instanceof TypeVariable) {
                    TypeVariable<?> v = (TypeVariable<?>) actualTypeParam;
                    currentClass = thisClass;
                    if (!(v.getGenericDeclaration() instanceof Class)) {
                        return Object.class;
                    }

                    parameterizedSuperclass = (Class<?>) v.getGenericDeclaration();
                    typeParamName = v.getName();
                    if (parameterizedSuperclass.isAssignableFrom(thisClass)) {
                        continue;
                    } else {
                        return Object.class;
                    }
                }
                return fail(thisClass, typeParamName);
            }
            currentClass = currentClass.getSuperclass();
            if (null == currentClass) {
                return fail(this.getClass(), typeParamName);
            }
        }
    }

    private Class<?> fail(Class<?> type, String typeParamName) {
        throw new IllegalStateException(
                "cannot determine the type of the type parameter '" + typeParamName + "': " + type);
    }

    private static TypeParameterTypeFinder slowTypeParameterTypeFinder;


    private TypeParameterTypeFinder() {
    }


}

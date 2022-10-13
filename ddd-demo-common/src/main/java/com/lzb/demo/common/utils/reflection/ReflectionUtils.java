package com.lzb.demo.common.utils.reflection;

import com.lzb.demo.common.utils.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

public class ReflectionUtils {
    private static final String METHOD = "writeReplace";
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    public ReflectionUtils() {
    }

    public static <T, R> String getFieldName(PropertyFunc<T, R> func) {
        try {
            Method method = func.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda)method.invoke(func);
            String getter = serializedLambda.getImplMethodName();
            return resolveFieldName(getter);
        } catch (ReflectiveOperationException var4) {
            throw new RuntimeException(var4);
        }
    }

    private static String resolveFieldName(String getMethodName) {
        if (getMethodName.startsWith("get")) {
            getMethodName = getMethodName.substring(3);
        } else if (getMethodName.startsWith("is")) {
            getMethodName = getMethodName.substring(2);
        }

        return firstToLowerCase(getMethodName);
    }

    private static String firstToLowerCase(String param) {
        if (DataUtils.isEmpty(param)) {
            return "";
        } else {
            String var10000 = param.substring(0, 1).toLowerCase();
            return var10000 + param.substring(1);
        }
    }

    public static <T> boolean isExistFieldName(String fieldName, Class<T> clazz) {
        boolean flag = false;
        Field[] fields = clazz.getDeclaredFields();
        Field[] var4 = fields;
        int var5 = fields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            if (field.getName().equals(fieldName)) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public static <T, V extends Annotation> boolean isExistFieldName(String fieldName, Class<T> clazz, Class<V> annotationClass) {
        boolean flag = false;
        Field[] fields = clazz.getDeclaredFields();
        Field[] var5 = fields;
        int var6 = fields.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Field field = var5[var7];
            if (field.getName().equals(fieldName) && Objects.nonNull(field.getAnnotation(annotationClass))) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public static Object getPropertyValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception var3) {
            logger.error("getPropertyValue exception", var3);
            return null;
        }
    }

    public static void setPropertyValue(Object object, String fieldName, Object value) {
        try {
            Field f = object.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(object, value);
        } catch (Exception var4) {
            logger.error("setPropertyValue exception", var4);
        }

    }

    public static Constructor getConstructor(String clazzPath, Class<?>... parameterTypes) throws ClassNotFoundException, NoSuchMethodException {
        Class<?> operationClazz = Class.forName(clazzPath);
        return operationClazz.getDeclaredConstructor(parameterTypes);
    }
}
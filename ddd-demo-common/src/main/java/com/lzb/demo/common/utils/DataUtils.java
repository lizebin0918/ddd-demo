package com.lzb.demo.common.utils;

import org.apache.commons.lang3.RandomUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public final class DataUtils {
    public DataUtils() {
    }

    public static boolean isEmpty(Object pObj) {
        if (pObj == null) {
            return true;
        } else if (pObj == "") {
            return true;
        } else if (pObj instanceof String) {
            return ((String)pObj).trim().length() == 0;
        } else if (pObj instanceof Collection) {
            return ((Collection)pObj).size() == 0;
        } else if (pObj instanceof Map) {
            return ((Map)pObj).size() == 0;
        } else if (pObj instanceof Object[]) {
            return ((Object[])pObj).length == 0;
        } else if (pObj instanceof boolean[]) {
            return ((boolean[])pObj).length == 0;
        } else if (pObj instanceof byte[]) {
            return ((byte[])pObj).length == 0;
        } else if (pObj instanceof char[]) {
            return ((char[])pObj).length == 0;
        } else if (pObj instanceof short[]) {
            return ((short[])pObj).length == 0;
        } else if (pObj instanceof int[]) {
            return ((int[])pObj).length == 0;
        } else if (pObj instanceof long[]) {
            return ((long[])pObj).length == 0;
        } else if (pObj instanceof float[]) {
            return ((float[])pObj).length == 0;
        } else if (pObj instanceof double[]) {
            return ((double[])pObj).length == 0;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(Object pObj) {
        return !isEmpty(pObj);
    }

    public static boolean isAllNotEmpty(Object... objects) {
        Object[] var1 = objects;
        int var2 = objects.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Object object = var1[var3];
            if (isEmpty(object)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAllEmpty(Object... objects) {
        Object[] var1 = objects;
        int var2 = objects.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Object object = var1[var3];
            if (isNotEmpty(object)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAnyIsEmpty(Object... objects) {
        Object[] var1 = objects;
        int var2 = objects.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Object object = var1[var3];
            if (isEmpty(object)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isAnyNotEmpty(Object... objects) {
        Object[] var1 = objects;
        int var2 = objects.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Object object = var1[var3];
            if (isNotEmpty(object)) {
                return true;
            }
        }

        return false;
    }

    public static <T> T getDefaultValue(T value, T defaultValue) {
        return getDefaultValue(value, defaultValue, (a) -> {
            return a;
        });
    }

    public static <T, S> T getDefaultValue(S value, T defaultValue, Function<S, T> function) {
        return isNotEmpty(value) ? function.apply(value) : defaultValue;
    }

    public static <T, S> T getDefaultValueSafety(S value, T defaultValue, Function<S, T> function) {
        try {
            return isNotEmpty(value) ? function.apply(value) : defaultValue;
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public static <T> T getValueSafety(Supplier<T> dataGetter, T defaultValue) {
        try {
            return dataGetter.get();
        } catch (Exception var3) {
            return defaultValue;
        }
    }

    public static long randomLongId() {
        return -RandomUtils.nextLong(0L, 99999999999999L);
    }

    public static boolean tryParseLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static boolean isAllNotNull(Object... objects) {
        Object[] var1 = objects;
        int var2 = objects.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Object object = var1[var3];
            if (isEmpty(object)) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkObjFieldIsNull(Object obj) throws IllegalAccessException {
        Field[] var1 = obj.getClass().getDeclaredFields();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Field f = var1[var3];
            f.setAccessible(true);
            if (isNotEmpty(f.get(obj))) {
                return false;
            }
        }

        return true;
    }
}

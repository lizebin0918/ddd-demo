package com.lzb.demo.common.enumaration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举工具类
 * public static void main(String[] args) {
 *     boolean valueExist = EnumUtils.getByValue(TypeEnum.values(), 1).isPresent();
 *     System.out.println(valueExist);
 *     System.out.println(EnumUtils.getByValue(TypeEnum.values(), 4).isPresent());
 *     System.out.println(EnumUtils.getByValue(TypeEnum.class, 4).isPresent());
 *     System.out.println(EnumUtils.getByValue(StatusEnum.class, "all", String.CASE_INSENSITIVE_ORDER).isPresent());
 *     System.out.println(EnumUtils.getByValue(StatusEnum.class, "all1", String.CASE_INSENSITIVE_ORDER).isPresent());
 *
 * }
 */
public final class EnumUtils {

    private EnumUtils() {}

    private static final ConcurrentHashMap<Class<? extends Enum<?>>, Enum[]> cache = new ConcurrentHashMap<>();

    /**
     * 根据枚举值获取对应的枚举对象
     *
     * @param enums
     * @param value
     * @param <E>
     * @param <V>
     * @return
     */
    private static <E extends Enum<? extends EnumValue<V>>, V> Optional<E> getByValue(E[] enums, V value) {
        for (E e : enums) {
            if (((EnumValue<V>) e).getValue().equals(value)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    private static <E extends Enum<? extends EnumValue<V>>, V> List<E> listByValue(E[] enums, V value) {
        List<E> resultList = new ArrayList<>(enums.length);
        for (E e : enums) {
            if (((EnumValue<V>) e).getValue().equals(value)) {
                resultList.add(e);
            }
        }
        return resultList;
    }

    public static <E extends Enum<? extends EnumValue<V>>, V> List<E> listByValue(Class<E> enumClazz, V value) {
        return listByValue(cache.computeIfAbsent(enumClazz, Class::getEnumConstants), value);
    }

    /**
     * 根据枚举值获取对应的枚举对象
     *
     * @param enums      枚举集合
     * @param value      输入值
     * @param comparator 比较器
     * @param <E>        枚举类型
     * @param <V>        枚举值
     * @return 对应枚举
     */
    @SuppressWarnings("unchecked")
    private static <E extends Enum<? extends EnumValue<V>>, V> Optional<E> getByValue(
            E[] enums, V value, Comparator<V> comparator) {
        for (E e : enums) {
            if (comparator.compare(((EnumValue<V>) e).getValue(), value) == 0) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    /**
     * 根据枚举值获取对应的枚举对象
     *
     * @param enumClass 枚举class
     * @return 枚举对象
     */
    public static <E extends Enum<? extends EnumValue<V>>, V> Optional<E> getByValue(Class<E> enumClass, V value) {
        return getByValue(cache.computeIfAbsent(enumClass, Class::getEnumConstants), value);
    }

    /**
     * 根据枚举值获取对应的枚举对象
     *
     * @param enumClass
     * @param value
     * @param comparator
     * @param <E>
     * @param <V>
     * @return
     */
    public static <E extends Enum<? extends EnumValue<V>>, V> Optional<E> getByValue(
            Class<E> enumClass, V value, Comparator<V> comparator) {
        return getByValue(cache.computeIfAbsent(enumClass, Class::getEnumConstants), value, comparator);
    }

}
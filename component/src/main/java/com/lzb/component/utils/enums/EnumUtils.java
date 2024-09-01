package com.lzb.component.utils.enums;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import lombok.experimental.UtilityClass;

/**
 * 枚举工具类
 * public static void main(String[] args) {
 * System.out.println(EnumUtils.getByValue(TypeEnum.class, 4).isPresent());
 * System.out.println(EnumUtils.getByValue(StatusEnum.class, "all", String.CASE_INSENSITIVE_ORDER).isPresent());
 * System.out.println(EnumUtils.getByValue(StatusEnum.class, "all1", String.CASE_INSENSITIVE_ORDER).isPresent());
 * <p>
 * }
 *
 * @author lizebin
 * @date 2022/06/25
 */
@UtilityClass
public final class EnumUtils {

    private static final ConcurrentHashMap<Class<? extends Enum<?>>, Enum[]> cache = new ConcurrentHashMap<>();

    /**
     * 获取枚举数组
     *
     * @param enumClass 枚举类
     * @return {@link Enum[]}
     */
    private static <E extends Enum<?>> Enum[] getEnums(Class<E> enumClass) {
        Enum[] enums = cache.get(enumClass);
        if (Objects.isNull(enums)) {
            enums = cache.computeIfAbsent(enumClass, Class::getEnumConstants);
        }
        return enums;
    }

    /**
     * 根据枚举值获取对应的枚举对象
     *
     * @param enums      枚举集合
     * @param value      输入值
     * @param comparator 比较器
     * @return 对应枚举
     */
    @SuppressWarnings("unchecked")
    private static <E extends Enum<? extends EnumValue<V>>, V> Optional<E> getByValue(
            E[] enums, V value, Comparator<V> comparator) {
        if (value == null) {
            return Optional.empty();
        }
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
     * @param value     价值
     * @return 枚举对象
     */
    public static <E extends Enum<? extends EnumValue<V>>, V> Optional<E> getByValue(Class<E> enumClass, V value) {
        if (value == null) {
            return Optional.empty();
        }
        E[] enums = (E[]) getEnums(enumClass);
        for (E e : enums) {
            if (((EnumValue<V>) e).getValue().equals(value)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    /**
     * 根据枚举值获取对应的枚举对象
     *
     * @param enumClass  枚举类
     * @param value      价值
     * @param comparator 比较器
     * @return {@link Optional}<{@link E}>
     */
    public static <E extends Enum<? extends EnumValue<V>>, V> Optional<E> getByValue(
            Class<E> enumClass, V value, Comparator<V> comparator) {
        return getByValue(getEnums(enumClass), value, comparator);
    }

}
package com.lzb.demo.common.aggregate;

import com.lzb.demo.common.common.utils.DataUtils;
import com.lzb.demo.common.common.utils.reflection.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.PropertyChange;
import org.javers.core.diff.custom.CustomValueComparator;
import org.javers.core.metamodel.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;

import static org.javers.core.diff.ListCompareAlgorithm.LEVENSHTEIN_DISTANCE;


/**
 * diff差异
 *
 * @author lizebin
 * @date 2022/07/14
 */
@Slf4j
public class MyDiff<T extends Serializable> {

    public static final String ID_FIELD = "id";

    private static final Javers javers = JaversBuilder.javers()
            .withListCompareAlgorithm(LEVENSHTEIN_DISTANCE)
            // BigDecimal比较问题，将equal修改为compareTo, new BigDecimal("1.2")和new BigDecimal("1.20")
            .registerValue(BigDecimal.class, new CustomFixedEqualBigDecimalComparator())
            .build();

    private final Diff diff;

    public MyDiff(T oldVersion, T currentVersion) {
        initNegativeId(currentVersion);
        this.diff = javers.compare(oldVersion, currentVersion);
        setNullIfNegativeId(currentVersion);
    }

    public boolean hasChanges() {
        return diff.hasChanges();
    }

    /**
     * @param oldList       旧list
     * @param newList       新list
     * @param clazz         class
     * @param addConsume    新增
     * @param updateConsume 修改
     * @param removeConsume 删除
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> void listChangeFunction(List<T> oldList,
                                              List<T> newList,
                                              Class<T> clazz,
                                              Consumer<List<T>> addConsume,
                                              Consumer<List<T>> updateConsume,
                                              Consumer<List<T>> removeConsume) {

        initNegativeId(clazz, newList);
        Diff listDiff = javers.compareCollections(oldList, newList, clazz);
        Map<String, T> addMap = new HashMap<>();
        Map<String, T> removeMap = new HashMap<>();
        Map<String, T> updateMap = new HashMap<>();

        for (Change change : listDiff.getChanges()) {
            if ((change instanceof NewObject)) {
                Object newObject = change.getAffectedObject().get();
                setNullIfNegativeId((T) newObject);
                addMap.put(change.getAffectedLocalId().toString(), (T) newObject);
            }

            if ((change instanceof ObjectRemoved)) {
                removeMap.put(change.getAffectedLocalId().toString(), (T) change.getAffectedObject().get());
            }

            if ((change instanceof PropertyChange)) {
                if (change.getAffectedLocalId() != null && !addMap.containsKey(change.getAffectedLocalId().toString())) {
                    updateMap.put(change.getAffectedLocalId().toString(), (T) change.getAffectedObject().get());
                }
            }
        }
        if (DataUtils.isAllNotNull(addMap.values(), addConsume)) {
            addConsume.accept(new ArrayList<>(addMap.values()));
        }
        if (DataUtils.isAllNotNull(updateMap.values(), updateConsume)) {
            updateConsume.accept(new ArrayList<>(updateMap.values()));
        }
        if (DataUtils.isAllNotNull(removeMap.values(), removeConsume)) {
            removeConsume.accept((new ArrayList<>(removeMap.values())));
        }
    }

    /**
     * id初始化负数
     *
     * @param clazz
     * @param list
     * @param <T>
     */
    public static <T extends Serializable> void initNegativeId(Class<T> clazz, Collection<T> list) {
        // 检查是否有id 属性
        if (ReflectionUtils.isExistFieldName(ID_FIELD, clazz, Id.class)) {
            for (T t : list) {
                if (Objects.isNull(t)) {
                    continue;
                }
                Object id = ReflectionUtils.getPropertyValue(t, ID_FIELD);
                if (DataUtils.isEmpty(id)) {
                    ReflectionUtils.setPropertyValue(t, ID_FIELD, DataUtils.randomLongId());
                }
            }
        }
    }

    /**
     * 默认初始化负数
     *
     * @param entity
     * @param <T>
     */
    public static <T extends Serializable> void initNegativeId(T entity) {
        if (Objects.isNull(entity)) {
            return;
        }

        // 检查是否有id 属性
        if (ReflectionUtils.isExistFieldName(ID_FIELD, entity.getClass(), Id.class)) {
            Object id = ReflectionUtils.getPropertyValue(entity, ID_FIELD);
            if (DataUtils.isEmpty(id)) {
                ReflectionUtils.setPropertyValue(entity, ID_FIELD, DataUtils.randomLongId());
            }
        }
    }

    public static <T extends Serializable> void setNullIfNegativeId(T entity) {
        if (Objects.isNull(entity)) {
            return;
        }

        // 检查是否有id 属性
        if (ReflectionUtils.isExistFieldName(ID_FIELD, entity.getClass(), Id.class)) {
            Object id = ReflectionUtils.getPropertyValue(entity, ID_FIELD);
            if (DataUtils.isNotEmpty(id)) {
                if (id instanceof Number && new BigDecimal(Objects.toString(id)).compareTo(BigDecimal.ZERO) < 0) {
                    ReflectionUtils.setPropertyValue(entity, ID_FIELD, null);
                }
            }
        }
    }

    public static class CustomFixedEqualBigDecimalComparator implements CustomValueComparator<BigDecimal> {

        @Override
        public boolean equals(BigDecimal a, BigDecimal b) {
            return a.compareTo(b) == 0;
        }

        @Override
        public String toString(BigDecimal value) {
            return value.toPlainString();
        }
    }

}

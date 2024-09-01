package com.lzb.component.mybatis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class MyDiff<T extends BasePo> {

    /**
     * @param oldList       旧list
     * @param newList       新list
     * @param clazz         class
     * @param addConsume    新增
     * @param updateConsume 修改
     * @param removeConsume 删除
     * @param <T>
     */
    public static <T extends BasePo> void listChange(List<T> oldList,
            List<T> newList,
            Class<T> clazz,
            Consumer<List<T>> addConsume,
            Consumer<List<T>> updateConsume,
            Consumer<List<T>> removeConsume) {

        collectionChange(oldList, newList, clazz,
                addMap -> addConsume.accept(new ArrayList<>(addMap.values())),
                updateMap -> updateConsume.accept(new ArrayList<>(updateMap.values())),
                removeMap -> removeConsume.accept(new ArrayList<>(removeMap.values())));
    }

    /**
     * @param oldList
     * @param newList
     * @param clazz
     * @param addConsume
     * @param updateConsume
     * @param removeConsume
     * @param <T>
     */
    public static <T extends BasePo> void collectionChange(Collection<T> oldList,
            Collection<T> newList,
            Class<T> clazz,
            Consumer<Map<String, T>> addConsume,
            Consumer<Map<String, T>> updateConsume,
            Consumer<Map<String, T>> removeConsume) {

        Map<String, T> addMap = new HashMap<>();
        Map<String, T> removeMap = new HashMap<>();
        Map<String, T> updateMap = new HashMap<>();

        Set<String> oldIds = oldList.stream()
                .map(MyDiff::getUniqueId)
                .collect(Collectors.toSet());

        for (T newItem : newList) {
            if (!oldIds.contains(getUniqueId(newItem))) {
                addMap.put(getUniqueId(newItem), newItem);
            }
        }

        Set<String> newIds = newList.stream()
                .map(MyDiff::getUniqueId)
                .collect(Collectors.toSet());
        for (T oldItem : oldList) {
            if (!newIds.contains(getUniqueId(oldItem))) {
                removeMap.put(getUniqueId(oldItem), oldItem);
            }
        }

        for (T oldItem : oldList) {
            Optional<T> matchingNewItem = newList.stream()
                    .filter(newItem -> Objects.equals(getUniqueId(oldItem), getUniqueId(newItem)))
                    .findFirst();

            matchingNewItem.ifPresent(newItem -> {
                DiffNode diff = ObjectDifferBuilder.buildDefault().compare(newItem, oldItem);
                if (!diff.hasChanges()) {
                    return;
                }
                diff.visit((node, visit) -> {
                    if (!node.isRootNode() && node.hasChanges()) {
                        updateMap.put(getUniqueId(newItem), newItem);
                        visit.dontGoDeeper();
                    }
                });
            });
        }

        if (!addMap.isEmpty()) {
            addConsume.accept(addMap);
        }
        if (!updateMap.isEmpty()) {
            updateConsume.accept(updateMap);
        }
        if (!removeMap.isEmpty()) {
            removeConsume.accept(removeMap);
        }
    }

    /**
     * Extract unique ID for the object (need to customize based on object structure)
     */
    private static <T extends BasePo> String getUniqueId(T obj) {
        Serializable id = obj.id();
        return id == null ? null : id.toString();
    }

    /**
     * @param oldList
     * @param newList
     * @return left=新增集合(addList), right=更新集合(updateList)
     */
    public static <T extends BasePo> ImmutablePair<List<T>, List<T>> diff(Class<T> entityClass, List<T> oldList, List<T> newList) {
        List<T> addList = new ArrayList<>();
        List<T> updateList = new ArrayList<>();
        MyDiff.listChange(
                oldList,
                newList,
                entityClass,
                addList::addAll,
                updateList::addAll,
                removeList -> {
                }
        );
        return ImmutablePair.of(addList, updateList);
    }
}


package com.lzb.component.aggregate;

import java.util.Collection;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonIgnore;


public interface Identified<T, I> {

    @JsonIgnore
    default boolean isDuplicated() {
        var collection = getCollection();
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        long count = collection.stream().map(identify()).distinct().count();
        return count != collection.size();
    }

    @JsonIgnore
    Collection<T> getCollection();

    @JsonIgnore
    Function<T, I> identify();
}
package com.lzb.component.dto;

import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import one.util.streamex.StreamEx;

/**
 * <br/>
 * Created on : 2023-09-22 23:07
 * @author lizebin
 */
@Getter
@AllArgsConstructor
public class MyPageDto<T> {

    private final long pages;
    private final long pageSize;
    private final long total;
    private final List<T> rows;

    private <E> MyPageDto(long pages, long pageSize, long total, List<E> rows, Function<E, T> convertor) {
        this.pages = pages;
        this.pageSize = pageSize;
        this.total = total;
        this.rows = StreamEx.of(rows).map(convertor).toList();
    }

    public static <E, T> MyPageDto<T> of(long pages,
            long pageSize,
            long total,
            List<E> rows,
            Function<E, T> convertor) {
        return new MyPageDto<>(pages, pageSize, total, rows, convertor);
    }
}

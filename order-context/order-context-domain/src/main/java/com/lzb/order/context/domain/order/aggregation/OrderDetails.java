package com.lzb.order.context.domain.order.aggregation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lzb.component.aggregate.Identified;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;

/**
 * 订单聚合根关联对象-订单明细<br/>
 * 读方法：public
 * 写方法：default
 * Created on : 2022-03-07 19:57
 *
 * @author lizebin
 */
@Slf4j
public class OrderDetails implements Iterable<OrderDetail>, Serializable, Identified<OrderDetail, Long> {

    private final List<OrderDetail> list;

    /**
     * @JsonCreator 用于反序列化
     * @param list
     */
    @JsonCreator
    public OrderDetails(List<OrderDetail> list) {
        this.list = Objects.requireNonNullElseGet(list, ArrayList::new);
        validate();
    }

    void validate() {
        if (list.isEmpty()) {
            return;
        }
        if (isDuplicated()) {
            throw new IllegalArgumentException("订单明细id重复");
        }
    }

    @Override
    public Iterator<OrderDetail> iterator() {
        return list.iterator();
    }

    public Stream<OrderDetail> toStream() {
        return list.stream();
    }

    @Override
    public Collection<OrderDetail> getCollection() {
        return list;
    }

    @Override
    public Function<OrderDetail, Long> identify() {
        return OrderDetail::getId;
    }


    public Set<Integer> getSkuIds() {
        return StreamEx.of(list).map(OrderDetail::getSkuId).toSet();
    }

    public int count() {
        return list.size();
    }

    void add(OrderDetail orderDetail) {
        list.add(orderDetail);
    }
}
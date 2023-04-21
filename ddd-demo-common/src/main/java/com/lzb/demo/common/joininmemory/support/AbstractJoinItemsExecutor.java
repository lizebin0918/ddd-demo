package com.lzb.demo.common.joininmemory.support;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Preconditions;
import com.lzb.demo.common.joininmemory.JoinItemExecutor;
import com.lzb.demo.common.joininmemory.JoinItemsExecutor;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
abstract class AbstractJoinItemsExecutor<DATA>
        implements JoinItemsExecutor<DATA> {
    @Getter(AccessLevel.PROTECTED)
    private final Class<DATA> dataCls;
    @Getter(AccessLevel.PROTECTED)
    private final List<JoinItemExecutor<DATA>> joinItemExecutors;

    public AbstractJoinItemsExecutor(Class<DATA> dataCls,
                                     List<JoinItemExecutor<DATA>> joinItemExecutors) {
        Preconditions.checkArgument(dataCls != null);
        Preconditions.checkArgument(joinItemExecutors != null);

        this.dataCls = dataCls;
        this.joinItemExecutors = joinItemExecutors;
        Collections.sort(this.joinItemExecutors, Comparator.comparingInt(JoinItemExecutor::runOnLevel));
    }
}

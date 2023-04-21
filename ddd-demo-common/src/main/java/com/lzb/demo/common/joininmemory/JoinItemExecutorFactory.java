package com.lzb.demo.common.joininmemory;

import java.util.List;

/**
 * Created by taoli on 2022/8/5.
 * 
 * 
 */
public interface JoinItemExecutorFactory {
    <DATA> List<JoinItemExecutor<DATA>> createForType(Class<DATA> cls);
}

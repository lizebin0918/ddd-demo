package com.lzb.demo.common.joininmemory;

import java.util.List;

/**
 * Created by taoli on 2022/7/31.
 * 
 * 
 */
public interface JoinItemsExecutor<DATA> {
    void execute(List<DATA> datas);
}

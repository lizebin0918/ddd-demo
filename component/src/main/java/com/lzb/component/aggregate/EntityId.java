package com.lzb.component.aggregate;

/**
 * id接口，可以定义成泛型<br/>
 * Created on : 2024-04-03 22:11
 *
 * @author lizebin
 */
public interface EntityId<T> {

    /**
     * 获取id
     * @return
     */
    T id();

}

package com.lzb.component.id;

/**
 * <br/>
 * Created on : 2023-09-08 13:10
 * @author lizebin
 */
public interface IdGenerator {

    String BEAN_NAME = "idGenerator";

    /**
     * 生成id
     * @return
     */
    long id();

}

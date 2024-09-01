package com.lzb.component.id;

import cn.hutool.core.util.IdUtil;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-08 13:11
 * @author lizebin
 */
@Component(IdGenerator.BEAN_NAME)
public class IdGeneratorSnowflakeImpl implements IdGenerator {

    public long id() {
        return IdUtil.getSnowflakeNextId();
    }
}

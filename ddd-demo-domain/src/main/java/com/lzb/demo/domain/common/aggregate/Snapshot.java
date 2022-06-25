package com.lzb.demo.domain.common.aggregate;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/**
 * <br/>
 * Created on : 2022-04-16 16:04
 *
 * @author lizebin
 */
public class Snapshot<R extends Serializable> {

    /**
     * 反序列化无需通过无参构造函数:OffsetDateTime 通过字符串序列化/反序列化有误
     */
    /*private static final Gson GSON = new Gson();*/

    /**
     * 缓存聚合根快照（不能声明成static，static会导致内存异常）
     */
    private final ThreadLocal<R> context = new ThreadLocal<>();

    /**
     * 设置快照
     */
    @SuppressWarnings("unchecked")
    public void set(R root) {
        context.remove();
        context.set(SerializationUtils.clone(root));
    }

    /**
     * 获取快照
     * @return
     */
    public R get() {
        return context.get();
    }

    /**
     * 移除快照
     */
    public void remove() {
        context.remove();
    }

    private boolean containsNotAscii(String input) {
        for (char c : input.toCharArray()) {
            if (!CharUtils.isAscii(c)) {
                return true;
            }
        }
        return false;
    }

}

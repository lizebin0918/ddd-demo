package com.lzb.demo.infr.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

/**
 * 数据库实体:persitance object，字段（是否必填）跟数据库一一对应<br/>
 * Created on : 2022-04-19 16:42
 *
 * @author lizebin
 */
@Value // 所有字段都是final/getter
@Builder // 方便手动创建
//@TableName("\"user\"")
@AllArgsConstructor// mybatis自动装配
public class DemoPo {

    /**
     * id 主键
     */
    @NonNull
    @TableId(type = IdType.INPUT)
    Long id;

    /**
     * 状态
     */
    @NonNull
    Status status;

    /**
     * 手机号
     */
    String phone;

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Optional<String> getPhone() {
        return Optional.ofNullable(phone);
    }

    /**
     * <br/>
     * Created on : 2022-04-19 17:07
     *
     * @author lizebin
     */
    public enum Status {

    }
}

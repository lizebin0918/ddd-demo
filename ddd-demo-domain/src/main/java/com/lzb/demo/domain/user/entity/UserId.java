package com.lzb.demo.domain.user.entity;

import com.lzb.demo.domain.common.aggregate.EntityId;
import com.lzb.demo.domain.order.valobj.OrderId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * <br/>
 * Created on : 2022-02-14 15:50
 *
 * @author lizebin
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class UserId extends EntityId {

    public UserId(long id) {
        super(id);
    }

    public static UserId create(long id) {
        return new UserId(id);
    }
}

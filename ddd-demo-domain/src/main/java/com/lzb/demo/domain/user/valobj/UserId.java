package com.lzb.demo.domain.user.valobj;

import com.lzb.demo.domain.common.aggregate.EntityId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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

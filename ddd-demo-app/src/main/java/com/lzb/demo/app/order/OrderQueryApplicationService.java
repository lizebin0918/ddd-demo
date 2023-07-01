package com.lzb.demo.app.order;

import com.lzb.demo.common.rsp.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2022-03-01 06:57
 *
 * @author lizebin
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class OrderQueryApplicationService {

    /**
     * 分页查询，直接走Gateway，不经过domain
     * @return
     */
    public Result listForPage() {
        return null;
    }

}

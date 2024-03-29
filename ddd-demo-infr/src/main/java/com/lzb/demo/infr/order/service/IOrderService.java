package com.lzb.demo.infr.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzb.demo.infr.order.po.OrderDo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizebin
 * @since 2022-02-14
 */
public interface IOrderService extends IService<OrderDo> {

    /**
     * 根据id全字段更新
     *
     * @param orderDo
     * @return
     */
    boolean updateAllFieldsById(OrderDo orderDo);

}

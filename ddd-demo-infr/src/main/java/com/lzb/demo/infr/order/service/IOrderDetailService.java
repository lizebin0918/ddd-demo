package com.lzb.demo.infr.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzb.demo.infr.order.po.OrderDetailDo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizebin
 * @since 2022-02-14
 */
public interface IOrderDetailService extends IService<OrderDetailDo> {

    /**
     * 根据id批量全字段更新
     *
     * @param list
     */
    void updateAllFieldsForBatchById(List<OrderDetailDo> list);

}
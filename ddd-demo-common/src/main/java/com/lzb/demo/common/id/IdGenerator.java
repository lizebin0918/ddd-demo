package com.lzb.demo.common.id;

/**
 * <br/>
 * Created on : 2022-06-14 15:34
 *
 * @author lizebin
 */
public interface IdGenerator {

    /**
     * order.order_id
     */
    String OMS_ORDER_ID = "OMS_ORDER_ID";

    /**
     * order_detail.id
     */
    String OMS_ORDER_DETAIL_ID = "OMS_ORDER_DETAIL_ID";

    String OMS_ORDER_REFUND_ID = "OMS_ORDER_REFUND_ID";

    /**
     * domain_event.id
     */
    String OMS_DOMAIN_EVENT_ID = "OMS_DOMAIN_EVENT_ID";

    String OMS_ORDER_REFUND_DETAIL_ID = "OMS_ORDER_REFUND_DETAIL_ID";

    /**
     * 获取id
     *
     * @param key
     * @return
     */
    long get(final String key);

}

package com.lzb.order.context.domain.order.aggregation;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created on : 2024-08-08 23:17
 *
 * @author lizebin
 */
@Slf4j
@Getter
@Jacksonized
@SuperBuilder(toBuilder = true)
@Setter(AccessLevel.PACKAGE)
public class Extension implements Serializable {

    /**
     * 运费险
     */
    private BigDecimal shipInsuranceFee;

    /**
     * 首单标识
     */
    private Boolean isFirstOrder;

}

package com.lzb.order.context.domain.order.aggregation.valobj;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
public class FullAddressLine implements Serializable {

    private final String addressLine1;
    private final String addressLine2;
}

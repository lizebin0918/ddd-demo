package com.lzb.demo.domain.customer.gateway;

import com.lzb.demo.domain.customer.Customer;
import com.lzb.demo.domain.customer.Credit;

//Assume that the credit info is in antoher distributed Service
public interface CreditGateway {
    public Credit getCredit(String customerId);
}

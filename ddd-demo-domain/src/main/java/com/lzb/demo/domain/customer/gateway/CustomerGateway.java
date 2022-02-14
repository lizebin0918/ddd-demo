package com.lzb.demo.domain.customer.gateway;

import com.lzb.demo.domain.customer.Customer;

public interface CustomerGateway {
    public Customer getByById(String customerId);
}

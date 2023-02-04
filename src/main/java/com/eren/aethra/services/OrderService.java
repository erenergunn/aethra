package com.eren.aethra.services;

import com.eren.aethra.models.Order;

import java.util.List;

public interface OrderService {

    public List<Order> getOrdersForCustomer() throws Exception;

    public Order findOrderDetailsForCode(String orderCode) throws Exception;

}

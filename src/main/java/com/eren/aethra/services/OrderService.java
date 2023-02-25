package com.eren.aethra.services;

import com.eren.aethra.models.Order;

import java.util.List;

public interface OrderService {

    public List<Order> getOrdersForCustomer();

    public Order findOrderDetailsForCode(String orderCode) throws Exception;

    public void placeOrder(String addressCode) throws Exception;

}

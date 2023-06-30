package com.eren.aethra.services;

import com.eren.aethra.dtos.responses.OrderListResponse;
import com.eren.aethra.models.Order;

import java.util.List;

public interface OrderService {

    public OrderListResponse getOrdersForCustomer();

    public Order findOrderDetailsForCode(String orderCode) throws Exception;

    public void placeOrder() throws Exception;

}

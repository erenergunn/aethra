package com.eren.aethra.services.impl;

import com.eren.aethra.utils.JwtTokenUtil;
import com.eren.aethra.constants.Exceptions;
import com.eren.aethra.daos.OrderDao;
import com.eren.aethra.models.Customer;
import com.eren.aethra.models.Order;
import com.eren.aethra.services.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderDao orderDao;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public List<Order> getOrdersForCustomer() throws Exception {
        Customer customer = jwtTokenUtil.getUserFromToken();
        return orderDao.getOrdersByCustomer(customer);
    }

    @Override
    public Order findOrderDetailsForCode(String orderCode) throws Exception {
        Customer customer = jwtTokenUtil.getUserFromToken();
        Optional<Order> optionalOrder = orderDao.getOrderByCode(orderCode);
        if(optionalOrder.isPresent()){
            if(optionalOrder.get().getCustomer().equals(customer)){
                return optionalOrder.get();
            } else {
                throw new Exception(Exceptions.THIS_ORDER_DOES_BELONGS_TO_ANOTHER_CUSTOMER);
            }
        } else {
            throw new Exception(Exceptions.THERE_IS_NO_ORDER + orderCode);
        }
    }
}

package com.eren.aethra.services.impl;

import com.eren.aethra.constants.Exceptions;
import com.eren.aethra.daos.ModelDao;
import com.eren.aethra.daos.OrderDao;
import com.eren.aethra.enums.OrderStatus;
import com.eren.aethra.helpers.RatingCalculationHelper;
import com.eren.aethra.models.*;
import com.eren.aethra.services.AddressService;
import com.eren.aethra.services.CartService;
import com.eren.aethra.services.OrderService;
import com.eren.aethra.services.SessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultOrderService implements OrderService {

    @Resource
    OrderDao orderDao;

    @Resource
    ModelDao modelDao;

    @Resource
    private SessionService sessionService;

    @Resource
    AddressService addressService;

    @Resource
    CartService cartService;

    @Resource
    RatingCalculationHelper ratingCalculationHelper;

    @Override
    public List<Order> getOrdersForCustomer() {
        Customer customer = sessionService.getCurrentCustomer();
        return orderDao.getOrdersByCustomer(customer);
    }

    @Override
    public Order findOrderDetailsForCode(String orderCode) throws Exception {
        Customer customer = sessionService.getCurrentCustomer();
        Optional<Order> optionalOrder = orderDao.getOrderByCode(orderCode);
        if(optionalOrder.isPresent()){
            if(optionalOrder.get().getCustomer().equals(customer)){
                return optionalOrder.get();
            } else {
                throw new Exception(Exceptions.THIS_ORDER_BELONGS_TO_ANOTHER_CUSTOMER);
            }
        } else {
            throw new Exception(Exceptions.THERE_IS_NO_ORDER + orderCode);
        }
    }

    @Override
    public void placeOrder(String addressCode) throws Exception {
        Order order = new Order();
        Customer customer = sessionService.getCurrentCustomer();
        Cart cart = customer.getCart();
        Store store = cart.getCustomer().getStore();
        cartService.validateCart(cart);

        order.setEntries(cart.getEntries());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setCustomer(cart.getCustomer());
        order.setAddress(addressService.findAddressByCode(addressCode));
        order.setTotalPriceOfProducts(cart.getTotalPriceOfProducts());
        order.setShippingPrice(cart.getTotalPriceOfProducts() > store.getFreeShippingThreshold() ? 0 : order.getShippingPrice());
        order.setTotalPrice(order.getTotalPriceOfProducts() + order.getShippingPrice());
        modelDao.save(order);

        List<Entry> entries = cart.getEntries();
        List<Product> products = entries.stream().map(Entry::getProduct).collect(Collectors.toList());

        entries.forEach(entry -> {
            Product product = entry.getProduct();
            product.setStockValue(product.getStockValue() - entry.getQuantity());
            modelDao.save(product);
            ratingCalculationHelper.createOrRecalculateRatingOfP2P(products, product, 3D, 10);
            ratingCalculationHelper.createOrRecalculateRatingOfC2P(customer, product, 4D, 15);

        });

        cart.setEntries(Collections.emptyList());
        cart.setTotalPriceOfProducts(0D);
        modelDao.save(cart);
    }


}

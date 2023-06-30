package com.eren.aethra.services.impl;

import com.eren.aethra.constants.Exceptions;
import com.eren.aethra.daos.CartDao;
import com.eren.aethra.daos.ModelDao;
import com.eren.aethra.daos.OrderDao;
import com.eren.aethra.dtos.responses.EntryResponse;
import com.eren.aethra.dtos.responses.OrderListResponse;
import com.eren.aethra.dtos.responses.OrderResponse;
import com.eren.aethra.enums.OrderStatus;
import com.eren.aethra.helpers.RatingHelper;
import com.eren.aethra.models.*;
import com.eren.aethra.services.AddressService;
import com.eren.aethra.services.CartService;
import com.eren.aethra.services.OrderService;
import com.eren.aethra.services.SessionService;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultOrderService implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private ModelDao modelDao;

    @Resource
    private CartDao cartDao;

    @Resource
    private SessionService sessionService;

    @Resource
    private AddressService addressService;

    @Resource
    private CartService cartService;

    @Resource
    private RatingHelper ratingHelper;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public OrderListResponse getOrdersForCustomer() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy - HH:mm");
        Customer customer = sessionService.getCurrentCustomer();
        List<Order> orders = orderDao.getOrdersByCustomer(customer);
        List<OrderResponse> orderList = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(orders)) {
            for (Order order : orders) {
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setPk(order.getPk().toString());
                orderResponse.setTotalPrice(order.getTotalPrice());
                Set<EntryResponse> entries = order.getEntries()
                        .stream()
                        .map(entry -> modelMapper.map(entry, EntryResponse.class))
                        .collect(Collectors.toSet());
                orderResponse.setEntries(entries);
                orderResponse.setOrderStatus(getOrderStatusName(order.getOrderStatus()));
                orderResponse.setCreatedAt(dateFormat.format(order.getCreatedAt()));
                orderList.add(orderResponse);
            }
            OrderListResponse orderListResponse = new OrderListResponse();
            orderListResponse.setOrders(orderList);
            return orderListResponse;
        }
        return null;
    }

    @Override
    public Order findOrderDetailsForCode(String orderCode) throws Exception {
        Customer customer = sessionService.getCurrentCustomer();
        Optional<Order> optionalOrder = orderDao.getOrderByPk(orderCode);
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
    public void placeOrder() throws Exception {
        Order order = new Order();
        Customer customer = sessionService.getCurrentCustomer();
        Cart cart = sessionService.getCurrentCart();
        cartService.validateCart(cart);

        order.setEntries(cart.getEntries());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setCustomer(cart.getCustomer());
        order.setTotalPriceOfProducts(cart.getTotalPriceOfProducts());
        order.setTotalPrice(order.getTotalPriceOfProducts());
        order.setCreatedAt(new Date());
        modelDao.save(order);

        Set<Entry> entries = cart.getEntries();
        List<Product> products = entries.stream().map(Entry::getProduct).collect(Collectors.toList());

        entries.forEach(entry -> {
            Product product = entry.getProduct();
            int newStock = product.getStockValue() - entry.getQuantity();
            product.setStockValue(newStock);
            if (newStock <= 0) {product.setIsApproved(false);}
            modelDao.save(product);
            ratingHelper.createOrRecalculateRatingOfP2P(products, product, 2D, 10);
            ratingHelper.createOrRecalculateRatingOfC2P(customer, product, 4D, 15);

        });

        cart.setEntries(Collections.emptySet());
        cart.setTotalPriceOfProducts(0D);
        modelDao.save(cart);
    }

    private String getOrderStatusName(OrderStatus orderStatus) {
        switch(orderStatus) {
            case CREATED:
                return  "Oluşturuldu";
            case RECEIVED:
                return "Alındı";
            case RETURNED:
                return "İade Edildi";
            case SHIPPING:
                return "Kargoya Verildi";
            case CANCELLED:
                return "İptal Edildi";
            case COMPLETED:
                return "Tamamlandı";
            case DELIVERED:
                return "Teslim Edili";
            case IN_PREPARATION:
                return "Hazırlanıyor";
            case CANCEL_REQUEST_RECEIVED:
                return "İptal İsteği Alındı";
            case RETURN_REQUEST_RECEIVED:
                return "İade İsteği Alındı";
            default:
                return "Alındı";
        }
    }


}

package com.eren.aethra.services.impl;

import com.eren.aethra.dtos.responses.AddressResponse;
import com.eren.aethra.dtos.responses.CartResponse;
import com.eren.aethra.dtos.responses.CheckoutResponse;
import com.eren.aethra.models.Customer;
import com.eren.aethra.services.CheckoutService;
import com.eren.aethra.services.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Resource
    private SessionService sessionService;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public CheckoutResponse getCheckoutPage() {
        Customer customer = sessionService.getCurrentCustomer();
        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.setCart(modelMapper.map(customer.getCart(), CartResponse.class));
        checkoutResponse.setAddresses(customer.getAddresses().stream().map(address -> modelMapper.map(address, AddressResponse.class)).collect(Collectors.toList()));
        return checkoutResponse;
    }

}

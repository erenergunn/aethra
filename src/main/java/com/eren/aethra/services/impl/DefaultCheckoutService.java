package com.eren.aethra.services.impl;

import com.eren.aethra.daos.AddressDao;
import com.eren.aethra.dtos.responses.AddressResponse;
import com.eren.aethra.dtos.responses.CartResponse;
import com.eren.aethra.dtos.responses.CheckoutResponse;
import com.eren.aethra.models.Address;
import com.eren.aethra.models.Customer;
import com.eren.aethra.services.CheckoutService;
import com.eren.aethra.services.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultCheckoutService implements CheckoutService {

    @Resource
    private SessionService sessionService;

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private AddressDao addressDao;

    @Override
    public CheckoutResponse getCheckoutPage() {
        Customer customer = sessionService.getCurrentCustomer();
        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.setCart(modelMapper.map(sessionService.getCurrentCart(), CartResponse.class));
        List<Address> addresses = addressDao.getAddressesByCustomer(customer);
        if (addresses != null && addresses.size() > 0) {
            checkoutResponse.setAddresses(addresses.stream().map(address -> modelMapper.map(address, AddressResponse.class)).collect(Collectors.toList()));
        }
        return checkoutResponse;
    }

}

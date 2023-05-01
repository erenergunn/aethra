package com.eren.aethra.services.impl;

import com.eren.aethra.constants.Exceptions;
import com.eren.aethra.daos.AddressDao;
import com.eren.aethra.dtos.requests.AddressRequest;
import com.eren.aethra.models.Address;
import com.eren.aethra.models.Customer;
import com.eren.aethra.services.AddressService;
import com.eren.aethra.services.SessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@Service
public class DefaultAddressService implements AddressService {

    @Resource
    AddressDao addressDao;

    @Resource
    SessionService sessionService;

    @Override
    public Address findAddressByCode(String code) throws Exception {
        Optional<Address> optionalAddress = addressDao.getAddressByCodeAndCustomer(code, sessionService.getCurrentCustomer());
        if(optionalAddress.isPresent()) {
            return optionalAddress.get();
        } else {
            throw new Exception(Exceptions.ADDRESS_NOT_FOUND_CODE + code);
        }
    }

    @Override
    public void editAddress(String code, AddressRequest addressRequest) throws Exception {
        Optional<Address> optionalAddress = addressDao.getAddressByCodeAndCustomer(code, sessionService.getCurrentCustomer());
        if(optionalAddress.isPresent()){
            Address address = optionalAddress.get();
            address.setAddress(addressRequest.getAddress());
        } else {
            throw new Exception(Exceptions.ADDRESS_NOT_FOUND_CODE + code);
        }
    }

    @Override
    public void createAddress(AddressRequest addressRequest) throws Exception {
        Customer customer = sessionService.getCurrentCustomer();
        Address address = new Address();
        address.setCustomer(customer);
        if(Objects.nonNull(Objects.nonNull(addressRequest.getAddress()))) {
            address.setAddress((addressRequest.getAddress()));
            address.setCode(addressRequest.getCode());
        } else {
            throw new Exception("All fields must be filled.");
        }
    }

    @Override
    public void deleteAddress(String addressCode) throws Exception {
        Customer customer = sessionService.getCurrentCustomer();
        Optional<Address> optionalAddress = addressDao.getAddressByCodeAndCustomer(addressCode, customer);
        if (optionalAddress.isPresent()) {
            addressDao.delete(optionalAddress.get());
        } else {
            throw new Exception("Address not found for code: " + addressCode);
        }
    }

}

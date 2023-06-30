package com.eren.aethra.services;

import com.eren.aethra.dtos.requests.CustomerRequest;
import com.eren.aethra.dtos.responses.AddressResponse;
import com.eren.aethra.models.Customer;

import java.util.List;

public interface CustomerService {

    Customer findCustomerByUsername(String username) throws Exception;

    Boolean isCustomerExists(String username);

    Customer getCurrentCustomer() throws Exception;

    Customer register(Customer customer);

    void updateCustomer(CustomerRequest customerDto) throws Exception;

    void deleteCustomerByUid(String uid) throws Exception;

    List<AddressResponse> getAddressesForCustomer() throws Exception;

}

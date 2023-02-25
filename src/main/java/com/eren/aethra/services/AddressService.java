package com.eren.aethra.services;

import com.eren.aethra.dtos.requests.AddressRequest;
import com.eren.aethra.models.address.Address;

public interface AddressService {

    Address findAddressByCode(String code) throws Exception;

    void editAddress(String code, AddressRequest addressRequest) throws Exception;

    void createAddress(AddressRequest addressRequest) throws Exception;

    void deleteAddress(String addressCode) throws Exception;

}

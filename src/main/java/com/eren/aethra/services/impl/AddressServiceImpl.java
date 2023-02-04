package com.eren.aethra.services.impl;

import com.eren.aethra.constants.Exceptions;
import com.eren.aethra.daos.AddressDao;
import com.eren.aethra.dtos.requests.AddressRequest;
import com.eren.aethra.models.address.Address;
import com.eren.aethra.services.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    AddressDao addressDao;


    @Override
    public Address findAddressByCode(String code) throws Exception {
        Optional<Address> optionalAddress = addressDao.getAddressByCode(code);
        if(optionalAddress.isPresent()) {
            return optionalAddress.get();
        } else {
            throw new Exception(Exceptions.ADDRESS_NOT_FOUND_CODE + code);
        }
    }

    @Override
    public void editAddress(String code, AddressRequest addressRequest) throws Exception {
        Optional<Address> optionalAddress = addressDao.getAddressByCode(code);
        if(optionalAddress.isPresent()){
            // TODO : DTO'dan gelen veriler entity üzerine set edilecek
        } else {
            throw new Exception(Exceptions.ADDRESS_NOT_FOUND_CODE + code);
        }
    }
}

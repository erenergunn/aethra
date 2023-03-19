package com.eren.aethra.services.impl;

import com.eren.aethra.constants.Exceptions;
import com.eren.aethra.daos.AddressDao;
import com.eren.aethra.daos.CityDao;
import com.eren.aethra.daos.DistrictDao;
import com.eren.aethra.dtos.requests.AddressRequest;
import com.eren.aethra.models.Customer;
import com.eren.aethra.models.address.Address;
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
    CityDao cityDao;


    @Resource
    DistrictDao districtDao;

    @Resource
    SessionService sessionService;

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
            Address address = optionalAddress.get();
            address.setCity(cityDao.getCityByName(addressRequest.getCity()));
            address.setDistrict(districtDao.getDistrictByName(addressRequest.getDistrict()));
            address.setAddressLine(addressRequest.getAddress());
        } else {
            throw new Exception(Exceptions.ADDRESS_NOT_FOUND_CODE + code);
        }
    }

    @Override
    public void createAddress(AddressRequest addressRequest) throws Exception {
        Customer customer = sessionService.getCurrentCustomer();
        Address address = new Address();
        address.setCustomer(customer);
        if(Objects.nonNull(addressRequest.getCity()) && Objects.nonNull(addressRequest.getDistrict()) && Objects.nonNull(addressRequest.getAddress())) {
            address.setCity(cityDao.getCityByName(addressRequest.getCity()));
            address.setDistrict(districtDao.getDistrictByName(addressRequest.getDistrict()));
            address.setAddressLine((addressRequest.getAddress()));
        } else {
            throw new Exception("All fields must be filled.");
        }
    }

    @Override
    public void deleteAddress(String addressCode) throws Exception {
        Customer customer = sessionService.getCurrentCustomer();
        Address addressModel = customer.getAddresses().stream()
                .filter(address -> address.getCode().equals(addressCode))
                .findFirst()
                .orElseThrow(() -> new Exception(Exceptions.NO_ADDRESS_FOR_CUSTOMER + addressCode));
        addressDao.delete(addressModel);
    }

}

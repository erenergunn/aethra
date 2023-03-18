package com.eren.aethra.api.store;

import com.eren.aethra.constants.AethraCoreConstants;
import com.eren.aethra.dtos.requests.AddressRequest;
import com.eren.aethra.dtos.requests.CustomerRequest;
import com.eren.aethra.dtos.responses.AddressResponse;
import com.eren.aethra.dtos.responses.CustomerResponse;
import com.eren.aethra.services.AddressService;
import com.eren.aethra.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/my-account")
public class AccountController {

    @Resource
    private CustomerService customerService;

    @Resource
    AddressService addressService;

    @Resource
    ModelMapper modelMapper;

    @GetMapping("/edit-profile")
    public ResponseEntity editAccount() {
        try {
            return new ResponseEntity<>(modelMapper.map(customerService.getCurrentCustomer(), CustomerResponse.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit-profile")
    public ResponseEntity editAccount(@RequestBody CustomerRequest customerDto) {
        try {
            customerService.updateCustomer(customerDto);
            return new ResponseEntity<>(AethraCoreConstants.PROFILE_UPDATED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/addresses")
    public ResponseEntity getAddresses() {
        try {
            return new ResponseEntity<>(customerService.getAddressesForCustomer(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-address")
    public ResponseEntity createAddress(AddressRequest addressRequest) {
        try {
            addressService.createAddress(addressRequest);
            return new ResponseEntity<>(AethraCoreConstants.ADDRESS_ADDED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/edit-address/{addressCode}")
    public ResponseEntity editAddress(@PathVariable String addressCode) {
        try {
            return new ResponseEntity<>(modelMapper.map(addressService.findAddressByCode(addressCode), AddressResponse.class), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit-address/{addressCode}")
    public ResponseEntity editAddress(@PathVariable String addressCode, @RequestBody AddressRequest addressRequest) {
        try {
            addressService.editAddress(addressCode, addressRequest);
            return new ResponseEntity<>(AethraCoreConstants.ADDRESS_UPDATED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-address/{addressCode}")
    public ResponseEntity deleteAddress(@PathVariable String addressCode) {
        try {
            addressService.deleteAddress(addressCode);
            return new ResponseEntity<>(AethraCoreConstants.ADDRESS_DELETED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

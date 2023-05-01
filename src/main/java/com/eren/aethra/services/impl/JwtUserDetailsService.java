package com.eren.aethra.services.impl;

import com.eren.aethra.dtos.requests.CustomerRequest;
import com.eren.aethra.dtos.responses.CustomerResponse;
import com.eren.aethra.models.Customer;
import com.eren.aethra.services.CustomerService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Resource
    private CustomerService customerService;

    @Resource
    private PasswordEncoder bcryptEncoder;

    @Resource
    private ModelMapper modelMapper;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = this.customerService.findCustomerByUsername(username);
        return new User(customer.getUsername(), customer.getPassword(),
                new ArrayList<>());
    }

    public CustomerResponse register(CustomerRequest request) {
        Customer customer = modelMapper.map(request, Customer.class);
        customer.setPassword(bcryptEncoder.encode(customer.getPassword()));
        customer.setFavProducts(Collections.emptySet());
        customerService.register(customer);
        return modelMapper.map(customer, CustomerResponse.class);
    }

}
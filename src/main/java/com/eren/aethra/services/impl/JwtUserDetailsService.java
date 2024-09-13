package com.eren.aethra.services.impl;

import com.eren.aethra.dtos.requests.CustomerRequest;
import com.eren.aethra.dtos.responses.JwtResponse;
import com.eren.aethra.models.Customer;
import com.eren.aethra.services.CustomerService;
import com.eren.aethra.services.StoreService;
import com.eren.aethra.utils.JwtTokenUtil;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Resource
    private StoreService storeService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private UserDetailsService userDetailsService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = this.customerService.findCustomerByUsername(username);
        return new User(customer.getUsername(), customer.getPassword(),
                new ArrayList<>());
    }

    public JwtResponse register(CustomerRequest request) throws Exception {
        if (customerService.isCustomerExists(request.getUsername())) {
            throw new Exception("Bu mail adresi kullanılmaktadır.");
        }
        Customer customer = modelMapper.map(request, Customer.class);
        customer.setPassword(bcryptEncoder.encode(customer.getPassword()));
        customer.setFavProducts(Collections.emptySet());
        customer.setStore(storeService.getStoreModel());
        customerService.register(customer);

        UserDetails userDetails = userDetailsService.loadUserByUsername(customer.getUsername());

        return new JwtResponse(jwtTokenUtil.generateToken(userDetails), 0L);
    }

}
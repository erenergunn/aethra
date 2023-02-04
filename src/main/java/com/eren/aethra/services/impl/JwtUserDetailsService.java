package com.eren.aethra.services.impl;

import com.eren.aethra.models.Customer;
import com.eren.aethra.services.CustomerService;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Resource
    private CustomerService customerService;

    @Resource
    private PasswordEncoder bcryptEncoder;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = this.customerService.findCustomerByUsername(username);
        return new User(customer.getUsername(), customer.getPassword(),
                new ArrayList<>());
    }

    public Customer register(Customer customer) {
        customer.setPassword(bcryptEncoder.encode(customer.getPassword()));
        return customerService.register(customer);
    }

}
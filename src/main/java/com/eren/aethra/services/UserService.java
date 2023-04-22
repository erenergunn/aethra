package com.eren.aethra.services;

import com.eren.aethra.dtos.requests.CustomerRequest;
import com.eren.aethra.dtos.responses.CustomerResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    CustomerResponse getProfile();
    void updateProfile(CustomerRequest customerRequest);

}

package com.eren.aethra.dtos.requests;

import lombok.Data;

@Data
public class CustomerRequest {

    private String username;

    private String contactMail;

    private String firstName;

    private String lastName;

    private String password;

}

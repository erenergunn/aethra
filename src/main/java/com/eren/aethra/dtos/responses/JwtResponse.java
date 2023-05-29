package com.eren.aethra.dtos.responses;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private final String jwtToken;

    private final Long expiresIn;

    public JwtResponse(String jwttoken, Long expiresIn) {
        this.jwtToken = jwttoken;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return this.jwtToken;
    }

    public Long getExpiresIn() {
        return this.expiresIn;
    }
}
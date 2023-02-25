package com.eren.aethra.dtos.requests;

import com.eren.aethra.models.address.City;
import com.eren.aethra.models.address.District;
import lombok.Data;

@Data
public class AddressRequest {

    private City city;

    private District district;

    private String address;

}

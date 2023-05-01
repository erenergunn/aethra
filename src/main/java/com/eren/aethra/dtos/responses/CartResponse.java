package com.eren.aethra.dtos.responses;

import lombok.Data;

import java.util.Set;

@Data
public class CartResponse {

    Set<EntryResponse> entries;

    Double totalPriceOfProducts;

    CustomerResponse customer;

}

package com.eren.aethra.dtos.requests;

import lombok.Data;

import java.util.List;

@Data
public class ProductListRequest {

    List<ProductRequest> products;

}

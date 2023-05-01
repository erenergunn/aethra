package com.eren.aethra.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class ProductListResponse {

    CategoryResponse category;

    List<ProductResponse> products;

}

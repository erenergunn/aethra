package com.eren.aethra.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class ProductListResponse {

    private CategoryResponse category;

    private List<ProductResponse> products;

}

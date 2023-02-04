package com.eren.aethra.services;

import com.eren.aethra.dtos.responses.ProductListResponse;

public interface CategoryService {

    ProductListResponse getCategoryPage(String code) throws Exception;

}

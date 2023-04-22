package com.eren.aethra.services;

import com.eren.aethra.dtos.requests.ProductRequest;
import com.eren.aethra.models.Product;

public interface ProductService {

    Product findProductByCode(String code) throws Exception;

    public void createOrUpdateProduct(ProductRequest dto);

}

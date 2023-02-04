package com.eren.aethra.services;

import com.eren.aethra.models.Product;

public interface ProductService {

    Product findProductByCode(String code) throws Exception;

}

package com.eren.aethra.services;

import com.eren.aethra.dtos.requests.ProductRequest;
import com.eren.aethra.dtos.responses.ProductResponse;
import com.eren.aethra.models.Product;

import java.util.List;

public interface ProductService {

    Product findProductByCode(String code) throws Exception;

    List<ProductResponse> getRecommendedProducts() throws Exception;

    List<ProductResponse> getBestSellingProducts();

    public void createOrUpdateProduct(ProductRequest dto) throws Exception;

}

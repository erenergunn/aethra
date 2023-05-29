package com.eren.aethra.services;

import com.eren.aethra.dtos.requests.ProductListRequest;
import com.eren.aethra.dtos.requests.ProductRequest;
import com.eren.aethra.dtos.responses.ProductListResponse;
import com.eren.aethra.dtos.responses.ProductResponse;
import com.eren.aethra.models.Category;
import com.eren.aethra.models.Product;

import java.util.List;

public interface ProductService {

    Product findProductByCode(String code) throws Exception;

    ProductListResponse getRecommendedProducts();

    List<ProductResponse> getBestSellingProducts();

    public void createOrUpdateProductBulk(ProductListRequest dto) throws Exception;

    public void createOrUpdateProduct(ProductRequest dto) throws Exception;

    List<Product> getProductModelsForCategory(Category category);

}

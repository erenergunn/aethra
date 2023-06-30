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

    ProductListResponse getSimilarProducts(String code) throws Exception;

    ProductListResponse getRecommendedProducts();

    ProductListResponse getFavoriteProducts();

    List<ProductResponse> getBestSellingProducts();

    void createOrUpdateProductBulk(ProductListRequest dto) throws Exception;

    void deleteProductForCode(String code) throws Exception;

    void createOrUpdateProduct(ProductRequest dto) throws Exception;

    List<Product> getProductModelsForCategory(Category category);

    void addProductToFavorites(String productCode) throws Exception;

}

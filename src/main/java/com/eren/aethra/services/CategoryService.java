package com.eren.aethra.services;

import com.eren.aethra.dtos.requests.CategoryRequest;
import com.eren.aethra.dtos.responses.ProductListResponse;
import com.eren.aethra.models.Category;

public interface CategoryService {

    ProductListResponse getCategoryPage(String code) throws Exception;

    Category getCategoryForCode(String code);

    void createOrUpdateCategory(CategoryRequest categoryRequest);

}

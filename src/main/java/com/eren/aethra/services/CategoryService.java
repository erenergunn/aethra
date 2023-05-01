package com.eren.aethra.services;

import com.eren.aethra.dtos.requests.CategoryRequest;
import com.eren.aethra.dtos.responses.CategoryResponse;
import com.eren.aethra.dtos.responses.ProductListResponse;
import com.eren.aethra.models.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getCategories();

    ProductListResponse getCategoryPage(String code) throws Exception;

    Category getCategoryForCode(String code);

    void createOrUpdateCategory(CategoryRequest categoryRequest);

}

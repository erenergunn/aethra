package com.eren.aethra.services.impl;

import com.eren.aethra.constants.Exceptions;
import com.eren.aethra.daos.CategoryDao;
import com.eren.aethra.daos.ProductDao;
import com.eren.aethra.dtos.requests.CategoryRequest;
import com.eren.aethra.dtos.responses.CategoryResponse;
import com.eren.aethra.dtos.responses.ProductListResponse;
import com.eren.aethra.dtos.responses.ProductResponse;
import com.eren.aethra.models.Category;
import com.eren.aethra.models.Product;
import com.eren.aethra.services.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultCategoryService implements CategoryService {

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private ProductDao productDao;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryDao.findAll();
        return categories
                .stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductListResponse getCategoryPage(String code) throws Exception {

        ProductListResponse response = new ProductListResponse();

        Optional<Category> optionalCategory = categoryDao.getCategoryByCode(code);

        if(optionalCategory.isPresent()){
            response.setCategory(modelMapper.map(optionalCategory.get(), CategoryResponse.class));

            List<Product> productList = productDao.findProductsByCategory(optionalCategory.get());
            if(productList.isEmpty()){
                throw new Exception(Exceptions.PRODUCT_NOT_FOUND_FOR_CATEGORY + optionalCategory.get().getCode());
            }
            response.setCategory(modelMapper.map(optionalCategory.get(), CategoryResponse.class));
            response.setProducts(productList
                    .stream()
                    .map(product -> modelMapper.map(product, ProductResponse.class))
                    .collect(Collectors.toList()));

            return response;

        } else {
            throw new Exception(Exceptions.CATEGORY_NOT_FOUND_CODE + code);
        }

    }

    @Override
    public Category getCategoryForCode(String code) {
        Optional<Category> optionalCategory = categoryDao.getCategoryByCode(code);
        return optionalCategory.orElse(null);
    }

    @Override
    public void createOrUpdateCategory(CategoryRequest dto) {
        Category category;
        Optional<Category> optionalCategory = categoryDao.getCategoryByCode(dto.getCode());
        if (optionalCategory.isPresent()) {
            category = optionalCategory.get();
        } else {
            category = new Category();
            category.setCode(dto.getCode());
        }
        if (StringUtils.isNotBlank(dto.getName())) category.setName(dto.getName());
        if (StringUtils.isNotBlank(dto.getDescription())) category.setDescription(dto.getDescription());
        categoryDao.save(category);
    }

}

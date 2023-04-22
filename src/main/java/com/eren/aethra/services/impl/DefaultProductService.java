package com.eren.aethra.services.impl;

import com.eren.aethra.constants.Exceptions;
import com.eren.aethra.daos.ProductDao;
import com.eren.aethra.dtos.requests.ProductRequest;
import com.eren.aethra.models.Category;
import com.eren.aethra.models.Product;
import com.eren.aethra.services.CategoryService;
import com.eren.aethra.services.ProductService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@Service
public class DefaultProductService implements ProductService {

    @Resource
    private ProductDao productDao;

    @Resource
    private CategoryService categoryService;

    @Override
    public Product findProductByCode(String code) throws Exception {
        Optional<Product> optionalProduct = productDao.getProductByCode(code);
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        } else {
            throw new Exception(Exceptions.PRODUCT_NOT_FOUND_CODE + code);
        }
    }

    @Override
    public void createOrUpdateProduct(ProductRequest dto) {
        Product product;
        Optional<Product> optionalProduct = productDao.getProductByCode(dto.getCode());
        if (optionalProduct.isPresent()){
            product = optionalProduct.get();
        } else {
            product = new Product();
            product.setCode(dto.getCode());
        }
        if (StringUtils.isNotBlank(dto.getCategoryCode())) {
            Category category = categoryService.getCategoryForCode(dto.getCategoryCode());
            if (category != null) {
                product.setCategory(category);
            }
        }
        if (StringUtils.isNotBlank(dto.getName())) {
            product.setName(dto.getName());
        }
        if (StringUtils.isNotBlank(dto.getDescription())) {
            product.setDescription(dto.getDescription());
        }
        if (CollectionUtils.isNotEmpty(dto.getGalleryImages())) {
            product.setGalleryImages(dto.getGalleryImages());
        }
        if (CollectionUtils.isNotEmpty(dto.getKeywords())) {
            product.setKeywords(dto.getKeywords());
        }
        if (Objects.nonNull(dto.getStockValue())) {
            product.setStockValue(dto.getStockValue());
        }
        if (Objects.nonNull(dto.getPrice())) {
            product.setPrice(dto.getPrice());
        }
        if (Objects.nonNull(dto.getIsApproved())) {
            product.setIsApproved(dto.getIsApproved());
        }
        productDao.save(product);
    }
}

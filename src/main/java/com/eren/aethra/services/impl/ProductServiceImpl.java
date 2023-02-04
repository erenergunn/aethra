package com.eren.aethra.services.impl;

import com.eren.aethra.constants.Exceptions;
import com.eren.aethra.daos.ProductDao;
import com.eren.aethra.models.Product;
import com.eren.aethra.services.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductDao productDao;

    @Override
    public Product findProductByCode(String code) throws Exception {
        Optional<Product> optionalProduct = productDao.getProductByCode(code);
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        } else {
            throw new Exception(Exceptions.PRODUCT_NOT_FOUND_CODE + code);
        }
    }
}

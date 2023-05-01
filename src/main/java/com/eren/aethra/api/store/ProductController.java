package com.eren.aethra.api.store;

import com.eren.aethra.dtos.responses.ProductResponse;
import com.eren.aethra.models.Product;
import com.eren.aethra.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/p")
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private ModelMapper modelMapper;

    @GetMapping("/{productCode}")
    public ResponseEntity getProductPageByCode(@PathVariable String productCode){
        try {
            Product productModel = productService.findProductByCode(productCode);
            return new ResponseEntity<>(modelMapper.map(productModel, ProductResponse.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/recommendations")
    public ResponseEntity getRecommendedProducts() {
        try {
            return new ResponseEntity(productService.getRecommendedProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

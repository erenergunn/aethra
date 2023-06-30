package com.eren.aethra.api.store;

import com.eren.aethra.constants.AethraCoreConstants;
import com.eren.aethra.dtos.responses.ProductListResponse;
import com.eren.aethra.dtos.responses.ProductResponse;
import com.eren.aethra.models.Product;
import com.eren.aethra.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/similar/{productCode}")
    public ResponseEntity getSimilarProducts(@PathVariable String productCode){
        try {
            return new ResponseEntity<>(productService.getSimilarProducts(productCode), HttpStatus.OK);
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

    @PostMapping("/add-to-fav/{productCode}")
    public ResponseEntity addToFavorites(@PathVariable String productCode){
        try {
            productService.addProductToFavorites(productCode);
            return new ResponseEntity<>(modelMapper.map(AethraCoreConstants.PRODUCT_ADDED_SUCCESSFULLY, ProductResponse.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/favorites")
    public ResponseEntity getFavoriteProducts() {
        try {
            return new ResponseEntity(productService.getFavoriteProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

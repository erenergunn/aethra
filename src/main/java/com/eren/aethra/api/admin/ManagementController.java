package com.eren.aethra.api.admin;

import com.eren.aethra.dtos.requests.CategoryListRequest;
import com.eren.aethra.dtos.requests.CategoryRequest;
import com.eren.aethra.dtos.requests.ProductListRequest;
import com.eren.aethra.dtos.requests.ProductRequest;
import com.eren.aethra.services.CategoryService;
import com.eren.aethra.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class ManagementController {

    @Resource
    private ProductService productService;

    @Resource
    private CategoryService categoryService;

    @PostMapping("/product/create-or-update")
    public ResponseEntity createOrUpdateProduct(@RequestBody ProductRequest productRequest) {
        try {
            productService.createOrUpdateProduct(productRequest);
            return new ResponseEntity("Product created / updated successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error while creating / updating product: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/product/bulk")
    public ResponseEntity createOrUpdateProductBulk(@RequestBody ProductListRequest productListRequest) {
        try {
            productService.createOrUpdateProductBulk(productListRequest);
            return new ResponseEntity("Products created / updated successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error while creating / updating product: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/category/create-or-update")
    public ResponseEntity createOrUpdateCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            categoryService.createOrUpdateCategory(categoryRequest);
            return new ResponseEntity("Category created / updated successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error while creating / updating category: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/category/bulk")
    public ResponseEntity createOrUpdateCategoryBulk(@RequestBody CategoryListRequest categoryListRequest) {
        try {
            categoryService.createOrUpdateCategoryBulk(categoryListRequest);
            return new ResponseEntity("Category created / updated successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error while creating / updating category: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

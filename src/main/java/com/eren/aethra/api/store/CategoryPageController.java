package com.eren.aethra.api.store;

import com.eren.aethra.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/c")
public class CategoryPageController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/{categoryCode}")
    public ResponseEntity<Object> getCategoryPageByCode(@PathVariable String categoryCode) {
        try {
            return new ResponseEntity<>(categoryService.getCategoryPage(categoryCode), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

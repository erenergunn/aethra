package com.eren.aethra.api.store;

import com.eren.aethra.constants.AethraCoreConstants;
import com.eren.aethra.dtos.responses.CartResponse;
import com.eren.aethra.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @Resource
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getCartForCustomer(){
        try {
            return new ResponseEntity<>(cartService.getCartForCustomer(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-product")
    public ResponseEntity addProductToCart(@RequestParam String productCode, @RequestParam Integer qty) {
        try {
            cartService.addProductToCart(productCode,qty);
            return new ResponseEntity<>(AethraCoreConstants.PRODUCT_ADDED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/remove-product")
    public ResponseEntity removeProductFromCart(@RequestParam String productCode) {
        try {
            cartService.removeProductFromCart(productCode);
            return new ResponseEntity<>(AethraCoreConstants.PRODUCT_REMOVED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update-qty")
    public ResponseEntity updateQuantityOfProduct(@RequestParam String productCode, @RequestParam Integer qty) {
        try {
            cartService.updateProductQuantity(productCode, qty);
            return new ResponseEntity<>(AethraCoreConstants.QTY_UPDATED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}

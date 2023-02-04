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
public class CartPageController {

    @Resource
    private CartService cartService;

    @Resource
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Object> getCartForCustomer(){
        try {
            return new ResponseEntity<>(modelMapper.map(cartService.getCartForCustomer(), CartResponse.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-product")
    public ResponseEntity<Object> addProductToCart(@RequestParam String productCode, @RequestParam Integer qty) {
        try {
            cartService.addProductToCart(productCode,qty);
            return new ResponseEntity<>(AethraCoreConstants.PRODUCT_ADDED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/remove-product")
    public ResponseEntity<Object> removeProductFromCart(@RequestParam String productCode) {
        try {
            cartService.removeProductFromCart(productCode);
            return new ResponseEntity<>(AethraCoreConstants.PRODUCT_REMOVED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/increase-qty")
    public ResponseEntity<Object> increaseQuantityOfProduct(@RequestParam String productCode) {
        try {
            cartService.increaseProductQuantity(productCode);
            return new ResponseEntity<>(AethraCoreConstants.QTY_INCREASED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/decrease-qty")
    public ResponseEntity<Object> decreaseQuantityOfProduct(@RequestParam String productCode) {
        try {
            cartService.decreaseProductQuantity(productCode);
            return new ResponseEntity<>(AethraCoreConstants.QTY_DECREASED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}

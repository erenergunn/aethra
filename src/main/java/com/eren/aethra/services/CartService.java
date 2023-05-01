package com.eren.aethra.services;

import com.eren.aethra.models.Cart;

public interface CartService {

    Cart getCartForCustomer();
    void validateCart(Cart cart);
    void calculateCart(Cart cart);
    void addProductToCart(String productCode, Integer qty) throws Exception;
    void removeProductFromCart(String productCode) throws Exception;
    void updateProductQuantity(String productCode, Integer qty) throws Exception;

}

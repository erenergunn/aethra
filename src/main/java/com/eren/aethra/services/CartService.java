package com.eren.aethra.services;

import com.eren.aethra.models.Cart;

public interface CartService {

    Cart getCartForCustomer() throws Exception;
    void addProductToCart(String productCode, Integer qty) throws Exception;
    void removeProductFromCart(String productCode) throws Exception;
    void increaseProductQuantity(String productCode) throws Exception;
    void decreaseProductQuantity(String productCode) throws Exception;

}

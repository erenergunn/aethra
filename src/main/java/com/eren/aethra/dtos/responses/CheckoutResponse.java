package com.eren.aethra.dtos.responses;

import java.util.List;

public class CheckoutResponse {

    private CartResponse cart;

    private List<AddressResponse> addresses;

    public CartResponse getCart() {
        return cart;
    }

    public void setCart(CartResponse cart) {
        this.cart = cart;
    }

    public List<AddressResponse> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressResponse> addresses) {
        this.addresses = addresses;
    }

}

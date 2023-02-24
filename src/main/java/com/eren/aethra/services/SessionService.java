package com.eren.aethra.services;

import com.eren.aethra.models.Cart;
import com.eren.aethra.models.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class SessionService {

    private Customer currentCustomer;

    private Cart currentCart;

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Cart getCurrentCart() {
        return currentCart;
    }

    public void setCurrentCart(Cart currentCart) {
        this.currentCart = currentCart;
    }

}

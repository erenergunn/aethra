package com.eren.aethra.daos;

import com.eren.aethra.models.Cart;
import com.eren.aethra.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDao extends JpaRepository<Cart, Long> {

    Cart findCartByCustomer(Customer customer);

}

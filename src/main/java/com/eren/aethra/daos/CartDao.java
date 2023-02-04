package com.eren.aethra.daos;

import com.eren.aethra.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartDao extends JpaRepository<Cart, Long> {

    Optional<Cart> getCartByCode(String code);

}

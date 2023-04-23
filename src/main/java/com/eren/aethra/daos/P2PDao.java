package com.eren.aethra.daos;

import com.eren.aethra.models.Product;
import com.eren.aethra.models.Product2ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface P2PDao extends JpaRepository<Product2ProductRating, Long> {

    Product2ProductRating findBySourceAndAndRating(Product source, Product rating);

}

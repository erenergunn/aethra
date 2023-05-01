package com.eren.aethra.daos;

import com.eren.aethra.models.Product;
import com.eren.aethra.models.Product2ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface P2PDao extends JpaRepository<Product2ProductRating, Long> {

    Product2ProductRating findBySourceAndAndTarget(Product source, Product target);

    List<Product2ProductRating> getProduct2ProductRatingsByTarget(Product source);

}

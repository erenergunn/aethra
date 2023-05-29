package com.eren.aethra.daos;

import com.eren.aethra.models.Product;
import com.eren.aethra.models.Product2ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface P2PDao extends JpaRepository<Product2ProductRating, Long> {

    Product2ProductRating findBySourceAndAndTarget(Product source, Product target);

    List<Product2ProductRating> getProduct2ProductRatingsBySource(Product source);

    @Query(value = "SELECT * FROM aethra.p2p_rating ORDER BY rating DESC limit 12", nativeQuery = true)
    List<Product2ProductRating> getMostPopularProducts();

}

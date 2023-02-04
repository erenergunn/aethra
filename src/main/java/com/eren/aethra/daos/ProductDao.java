package com.eren.aethra.daos;

import com.eren.aethra.models.Category;
import com.eren.aethra.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends JpaRepository<Product, Long> {

    Optional<Product> getProductByCode(String code);

    List<Product> findProductsByCategory(Category category);

}

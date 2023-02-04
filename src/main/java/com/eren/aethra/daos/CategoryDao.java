package com.eren.aethra.daos;

import com.eren.aethra.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Long> {

    Optional<Category> getCategoryByCode(String code);

}

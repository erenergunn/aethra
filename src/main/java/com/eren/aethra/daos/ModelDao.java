package com.eren.aethra.daos;

import com.eren.aethra.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelDao extends JpaRepository<Item, Long> {
}

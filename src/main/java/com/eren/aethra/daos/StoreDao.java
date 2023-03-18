package com.eren.aethra.daos;

import com.eren.aethra.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreDao extends JpaRepository<Store, Long> {

    Store findStoreByCode(String code);

}

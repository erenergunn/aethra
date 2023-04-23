package com.eren.aethra.daos;

import com.eren.aethra.models.Customer;
import com.eren.aethra.models.Customer2ProductRating;
import com.eren.aethra.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface C2PDao extends JpaRepository<Customer2ProductRating, Long> {

    Customer2ProductRating findByCuAndCustomerAndAndProduct(Customer customer, Product product);

}

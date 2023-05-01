package com.eren.aethra.daos;

import com.eren.aethra.models.Customer;
import com.eren.aethra.models.Customer2ProductRating;
import com.eren.aethra.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface C2PDao extends JpaRepository<Customer2ProductRating, Long> {

    Set<Customer2ProductRating> findCustomer2ProductRatingsByCustomer (Customer customer);

    Customer2ProductRating findCustomer2ProductRatingsByCustomerAndAndProduct (Customer customer, Product product);

}

package com.eren.aethra.daos;

import com.eren.aethra.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerDao extends JpaRepository<Customer, Long> {

    Optional<Customer> getCustomerByUsername(String uid);

}

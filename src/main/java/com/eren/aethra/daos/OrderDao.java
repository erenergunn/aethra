package com.eren.aethra.daos;

import com.eren.aethra.models.Customer;
import com.eren.aethra.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends JpaRepository<Order, Long> {

    List<Order> getOrdersByCustomer(Customer customer);

    Optional<Order> getOrderByPk(String pk);

}

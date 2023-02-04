package com.eren.aethra.models;


import com.eren.aethra.enums.OrderStatus;
import com.eren.aethra.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order extends Item {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private String code;

    @OneToMany
    private List<Entry> orderEntries;

    private Double totalPriceOfProducts;

    private Double shippingPrice;

    private Double totalPrice;

    @JsonBackReference
    @JoinColumn(name = "customer_id", nullable = false)
    @ManyToOne
    private Customer customer;

    private PaymentMethod paymentMethod;

    private OrderStatus orderStatus;

    private String address;

    private String shippingTrackingLink;

}

package com.eren.aethra.models;


import com.eren.aethra.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
public class Order extends Item {

    @OneToMany(fetch = FetchType.EAGER)
    @Nullable
    private Set<Entry> entries;

    private Double totalPriceOfProducts;

    @ManyToOne
    private Customer customer;

    private Double totalPrice;

    private OrderStatus orderStatus;

    @ManyToOne
    private Address address;

    private String shippingTrackingLink;

}

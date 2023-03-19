package com.eren.aethra.models;


import com.eren.aethra.enums.OrderStatus;
import com.eren.aethra.models.address.Address;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "order")
public class Order extends Cart {

    private Double shippingPrice;

    private Double totalPrice;

    private OrderStatus orderStatus;

    @OneToOne
    private Address address;

    private String shippingTrackingLink;

}

package com.eren.aethra.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "store")
public class Store extends Item {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true)
    private String code;

    private String name;

    private String logoUrl;

    private Double shippingCost;

    private Double freeShippingThreshold;

}

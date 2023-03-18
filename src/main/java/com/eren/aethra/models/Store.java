package com.eren.aethra.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "store")
public class Store extends Item {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private String code;

    private String name;

    private String logoUrl;

    private Double shippingCost;

    private Double freeShippingThreshold;

}

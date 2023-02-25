package com.eren.aethra.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "carts")
public class Cart extends Item {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private String code;

    @OneToMany
    private List<Entry> entries;

    private Double totalPrice;

    @OneToOne
    private Customer customer;

}

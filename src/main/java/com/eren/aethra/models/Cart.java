package com.eren.aethra.models;

import jakarta.persistence.*;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.Set;

@Data
@Entity
@Table(name = "cart")
public class Cart extends Item {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true)
    private String code;

    @OneToMany(fetch = FetchType.EAGER)
    @Nullable
    private Set<Entry> entries;

    private Double totalPriceOfProducts;

    @OneToOne
    private Customer customer;

}

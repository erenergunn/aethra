package com.eren.aethra.models;

import com.sun.istack.Nullable;
import lombok.Data;

import javax.persistence.*;
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

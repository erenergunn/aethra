package com.eren.aethra.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "c2p_rating")
public class Customer2ProductRating extends Item {

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product product;

    private Double rating;

}

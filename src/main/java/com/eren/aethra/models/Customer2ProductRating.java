package com.eren.aethra.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

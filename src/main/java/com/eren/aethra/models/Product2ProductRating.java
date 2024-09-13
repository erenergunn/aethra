package com.eren.aethra.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "p2p_rating")
public class Product2ProductRating extends Item {

    @ManyToOne
    private Product source;

    @ManyToOne
    private Product target;

    private Double rating;

}

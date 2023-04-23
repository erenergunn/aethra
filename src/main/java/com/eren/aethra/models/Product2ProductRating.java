package com.eren.aethra.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

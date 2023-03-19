package com.eren.aethra.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "entry")
public class Entry extends Item {

    @OneToOne
    private Product product;

    private Integer quantity;

    @ManyToOne
    private Cart cart;

}

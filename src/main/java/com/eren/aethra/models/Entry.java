package com.eren.aethra.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "entry")
public class Entry extends Item {

    @OneToOne
    private Product product;

    private Integer quantity;

}

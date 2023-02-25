package com.eren.aethra.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "store")
public class Store extends Item {

    private String code;

    private String name;

    private String logoUrl;

    private Double shippingPrice;

}

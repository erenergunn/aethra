package com.eren.aethra.models;

import com.eren.aethra.models.Customer;
import com.eren.aethra.models.Item;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "address", uniqueConstraints = {@UniqueConstraint(columnNames = {"code", "customer"})})
public class Address extends Item {

    private String code;

    private String name;

    private String address;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer")
    private Customer customer;

}

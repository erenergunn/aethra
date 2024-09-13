package com.eren.aethra.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

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

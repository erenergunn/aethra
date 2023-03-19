package com.eren.aethra.models.address;

import com.eren.aethra.models.Customer;
import com.eren.aethra.models.Item;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "address")
public class Address extends Item {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private String code;

    @OneToOne
    private City city;

    @OneToOne
    private District district;

    private String addressLine;

    @ManyToOne
    private Customer customer;

}

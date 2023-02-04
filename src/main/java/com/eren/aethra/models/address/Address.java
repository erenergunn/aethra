package com.eren.aethra.models.address;

import com.eren.aethra.models.Item;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "addresses")
public class Address extends Item {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private String code;

    @OneToOne
    private City city;

    @OneToOne
    private District district;

    private String address;

}

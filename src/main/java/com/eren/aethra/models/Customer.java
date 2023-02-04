package com.eren.aethra.models;

import com.eren.aethra.models.address.Address;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "customers")
public class Customer extends Item {

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String contactMail;

    private String firstName;

    private String lastName;

    private String password;

    @OneToOne
    private Cart cart;

    @OneToMany
    private List<Product> favProducts;

    @OneToMany
    private List<Address> addresses;

    @ElementCollection
    private List<String> visitedProductsCodes;

}

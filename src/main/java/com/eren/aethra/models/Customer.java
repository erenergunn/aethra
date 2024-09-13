package com.eren.aethra.models;

import jakarta.persistence.*;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.Set;

@Data
@Entity
@Table(name = "customer")
public class Customer extends Item {

    @Column(unique = true)
    private String username;

    private String firstName;

    private String lastName;

    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @Nullable
    private Set<Product> favProducts;

    @ManyToOne
    private Store store;

}

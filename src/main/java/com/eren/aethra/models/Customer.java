package com.eren.aethra.models;

import com.sun.istack.Nullable;
import lombok.Data;

import javax.persistence.*;
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

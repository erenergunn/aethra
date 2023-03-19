package com.eren.aethra.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category extends Item {

    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String name;

    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;

}

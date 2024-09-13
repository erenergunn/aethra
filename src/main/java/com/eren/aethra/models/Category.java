package com.eren.aethra.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "category")
public class Category extends Item {

    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String name;

    private String description;

}

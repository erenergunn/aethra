package com.eren.aethra.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product extends Item {

    @Column(unique = true)
    private String code;

    private String name;

    private String description;

    private String picture;

    @ElementCollection
    private List<String> galleryImages;

    private Integer stockValue;

    private boolean isApproved;

    private Double price;

    @JsonBackReference
    @JoinColumn(name = "category", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ElementCollection
    private List<String> keywords;

}

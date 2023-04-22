package com.eren.aethra.dtos.requests;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {

    private String code;

    private String name;

    private String description;

    private String picture;

    private List<String> galleryImages;

    private Integer stockValue;

    private Boolean isApproved;

    private Double price;

    private String categoryCode;

    private List<String> keywords;

}

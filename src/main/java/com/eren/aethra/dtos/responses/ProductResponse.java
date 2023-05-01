package com.eren.aethra.dtos.responses;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProductResponse {

    String code;

    String name;

    String description;

    String picture;

    Set<String> galleryImages;

    Integer stockValue;

    Boolean isApproved;

    Double price;

    String categoryCode;

    Set<String> keywords;
}

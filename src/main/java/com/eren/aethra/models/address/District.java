package com.eren.aethra.models.address;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "districts")
public class District {

    @Id
    @Column(name = "code")
    private Integer code;

    private String name;

}

package com.eren.aethra.models.address;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "cities")
public class City {

    @Id
    @Column(name = "code")
    private Integer code;

    private String name;

    @OneToMany
    private List<District> districts;

}

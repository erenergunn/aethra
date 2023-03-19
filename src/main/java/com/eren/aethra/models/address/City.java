package com.eren.aethra.models.address;

import com.eren.aethra.models.Item;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "city")
public class City extends Item {

    private String name;

}

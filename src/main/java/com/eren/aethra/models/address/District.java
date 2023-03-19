package com.eren.aethra.models.address;

import com.eren.aethra.models.Item;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "district")
public class District extends Item {

    private String name;

    @ManyToOne
    private City city;

}

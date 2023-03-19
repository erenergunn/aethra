package com.eren.aethra.daos;

import com.eren.aethra.models.address.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDao extends JpaRepository<City, Integer> {

    City getCityByName(String name);

}

package com.eren.aethra.daos;

import com.eren.aethra.models.address.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictDao extends JpaRepository<District, Integer> {

    District getDistrictByName(String name);

}

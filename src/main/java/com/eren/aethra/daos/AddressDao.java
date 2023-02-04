package com.eren.aethra.daos;

import com.eren.aethra.models.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressDao extends JpaRepository<Address, Long> {

    Optional<Address> getAddressByCode(String code);

}

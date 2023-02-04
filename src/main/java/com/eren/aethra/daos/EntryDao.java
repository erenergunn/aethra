package com.eren.aethra.daos;

import com.eren.aethra.models.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryDao extends JpaRepository<Entry, Long> {
}

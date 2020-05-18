package com.example.residentevil.repository;

import com.example.residentevil.domain.entities.Capitals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CapitalsRepository extends JpaRepository<Capitals,Long> {
    @Query("SELECT c from Capitals as c order by c.name")
    List<Capitals> findByNameOrderByNameAsc();

    Capitals getById (Long id);
}

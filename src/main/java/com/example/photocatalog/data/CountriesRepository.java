package com.example.photocatalog.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CountriesRepository extends JpaRepository<CountriesEntity, UUID> {
    Optional<CountriesEntity> findByCountryCode(String countryCode);
}

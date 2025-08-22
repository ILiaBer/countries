package com.example.photocatalog.service;

import com.example.photocatalog.domain.Country;

import java.util.List;
import java.util.UUID;

public interface CountriesService {

    List<Country> allCountries();

    void addCountry(String countryName, String countryCode);

    void editCountryName(String countryCode, String countryName);

    Country getCountryById(UUID id);
}

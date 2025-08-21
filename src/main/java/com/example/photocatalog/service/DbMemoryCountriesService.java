package com.example.photocatalog.service;

import com.example.photocatalog.data.CountriesEntity;
import com.example.photocatalog.data.CountriesRepository;
import com.example.photocatalog.domain.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class DbMemoryCountriesService implements CountriesService {

    private final CountriesRepository countriesRepository;

    @Autowired
    public DbMemoryCountriesService(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }


    @Override
    public List<Country> allCountries() {
        return countriesRepository.findAll().stream().map(
                fe -> {
                    return new Country(
                            fe.getCountryName(),
                            fe.getCountryCode());
                }).toList();
    }

    @Override
    public void addCountry(String name, String code) {
        CountriesEntity countryEntity = new CountriesEntity();
        countryEntity.setCountryName(name);
        countryEntity.setCountryCode(code);
        countriesRepository.save(countryEntity);
    }

    @Override
    public void editCountryName(String countryCode, String newName) {
        CountriesEntity country = countriesRepository.findByCountryCode(countryCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Country not found with code: " + countryCode
                ));

        country.setCountryName(newName);
        countriesRepository.save(country);
    }
}
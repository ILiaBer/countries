package com.example.test.service;

import com.example.test.data.CountriesEntity;
import com.example.test.data.CountriesRepository;
import com.example.test.domain.Country;
import com.example.test.domain.graphql.CountryGql;
import com.example.test.domain.graphql.CountryInputGql;
import com.example.test.ex.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Component
public class DbCountriesService implements CountriesService {

    private final CountriesRepository countriesRepository;

    @Autowired
    public DbCountriesService(CountriesRepository countriesRepository) {
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
    public List<CountryGql> allGqlCountries() {
        return countriesRepository.findAll().stream().map(
                fe -> {
                    return new CountryGql(
                            fe.getId(),
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
    public CountryGql addCountry(CountryInputGql countryGql) {
        CountriesEntity countryEntity = new CountriesEntity();
        countryEntity.setCountryName(countryGql.name());
        countryEntity.setCountryCode(countryGql.code());
        CountriesEntity saved = countriesRepository.save(countryEntity);
        return new CountryGql(
                saved.getId(),
                countryGql.name(),
                countryGql.code());
    }

    @Override
    public Country editCountryName(String countryCode, String newName) {
        CountriesEntity country = countriesRepository.findByCountryCode(countryCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Country not found with code: " + countryCode
                ));

        country.setCountryName(newName);
        CountriesEntity saved = countriesRepository.save(country);
        return new Country(
                saved.getCountryName(),
                saved.getCountryCode());
    }

    @Override
    public CountryGql editCountryName(CountryInputGql inputGql, String newName) {
        return Country.toGqlCountry(editCountryName(inputGql.code(),newName));
    }

    @Override
    public Country getCountryById(UUID id) {
        return Country.fromGqlCountry(getCountryByGqlId(id));
    }

    @Override
    public CountryGql getCountryByGqlId(UUID id) {
        return countriesRepository.findById(id).map(
                fe ->
                        new CountryGql(
                                fe.getId(),
                                fe.getCountryName(),
                                fe.getCountryCode()
                        )).orElseThrow(CountryNotFoundException::new);
    }
}
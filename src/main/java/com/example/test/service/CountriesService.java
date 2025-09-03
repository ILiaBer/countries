package com.example.test.service;

import com.example.test.domain.Country;
import com.example.test.domain.graphql.CountryGql;
import com.example.test.domain.graphql.CountryInputGql;

import java.util.List;
import java.util.UUID;

public interface CountriesService {

    List<Country> allCountries();

    List<CountryGql> allGqlCountries();

    void addCountry(String countryName, String countryCode);

    CountryGql addCountry(CountryInputGql country);

    Country editCountryName(String countryCode, String countryName);

    CountryGql editCountryName(CountryInputGql country, String newName);

    Country getCountryById(UUID id);

    CountryGql getCountryByGqlId(UUID id);
}

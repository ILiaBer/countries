package com.example.test.domain;

import com.example.test.domain.graphql.CountryGql;

public record Country(String countryName, String countryCode) {

    public static Country fromGqlCountry(CountryGql countryGql) {
        return new Country(
                countryGql.name(),
                countryGql.code()
        );
    }

    public static CountryGql toGqlCountry(Country country) {
        return new CountryGql(
                null,
                country.countryName,
                country.countryCode
        );
    }
}

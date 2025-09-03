package com.example.test.controller.graphql;

import com.example.test.domain.graphql.CountryGql;
import com.example.test.service.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class CountriesQueryController {

    private CountriesService countriesService;

    @Autowired
    public CountriesQueryController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @QueryMapping
    public CountryGql country(@Argument UUID id) {
        return countriesService.getCountryByGqlId(id);
    }

    @QueryMapping
    public List<CountryGql> countries() {
        return countriesService.allGqlCountries();
    }
}

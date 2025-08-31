package com.example.test.controller.graphql;

import com.example.test.domain.graphql.CountryGql;
import com.example.test.domain.graphql.CountryInputGql;
import com.example.test.service.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CountriesMutationController {

    private CountriesService countriesService;

    @Autowired
    public CountriesMutationController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @MutationMapping
    public CountryGql addCountry(
            @Argument CountryInputGql input) {
        return countriesService.addCountry(input);
    }

    @MutationMapping
    public void editCountryName(
            @Argument CountryInputGql input, @Argument String newName) {
        countriesService.editCountryName(input, newName);
    }
}

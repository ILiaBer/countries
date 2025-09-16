package com.example.test.service.soap;

import com.example.test.domain.graphql.CountryGql;
import com.example.test.service.CountriesService;
import guru.qa.xml.countries.CountriesResponse;
import guru.qa.xml.countries.Country;
import guru.qa.xml.countries.CountryResponse;
import guru.qa.xml.countries.IdRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.UUID;

import static com.example.test.config.AppConfig.SOAP_NAMESPACE;

@Endpoint
public class CountryEndpoint {

    private final CountriesService service;

    @Autowired
    public CountryEndpoint(CountriesService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "idRequest")
    @ResponsePayload
    public CountryResponse getCountryById(@RequestPayload IdRequest request) {
        CountryGql countryGql = service.getCountryByGqlId(UUID.fromString(request.getId()));
        CountryResponse response = new CountryResponse();
        Country xmlCountry = new Country();
        xmlCountry.setId(countryGql.id().toString());
        xmlCountry.setName(countryGql.name());
        xmlCountry.setCode(countryGql.code());
        response.setCountry(xmlCountry);

        return response;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "allRequest")
    @ResponsePayload
    public CountriesResponse all() {
        List<CountryGql> countries = service.allGqlCountries();
        CountriesResponse response = new CountriesResponse();
        response.getCountries().addAll(countries.stream().map(
                        gqlCountry -> {
                            Country xmlCountry = new Country();
                            xmlCountry.setId(gqlCountry.id().toString());
                            xmlCountry.setName(gqlCountry.name());
                            xmlCountry.setCode(gqlCountry.code());
                            return xmlCountry;
                        }
                ).toList()
        );
        return response;
    }
}

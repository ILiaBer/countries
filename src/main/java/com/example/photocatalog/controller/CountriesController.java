package com.example.photocatalog.controller;

import com.example.photocatalog.domain.Country;
import com.example.photocatalog.service.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/countries")
public class CountriesController {

    private CountriesService countriesService;

    @Autowired
    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping("/all")
    public List<Country> all() {
        return countriesService.allCountries();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCountry(
            @RequestParam String name,
            @RequestParam String code) {
        countriesService.addCountry(name, code);
        return ResponseEntity.ok("Country added successfully");
    }

    @PatchMapping("/{code}/name")
    public void editCountryName(
            @PathVariable String code,
            @RequestParam String newName) {
        countriesService.editCountryName(code, newName);
    }

    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable("id") UUID id) {
        return countriesService.getCountryById(id);
    }
}

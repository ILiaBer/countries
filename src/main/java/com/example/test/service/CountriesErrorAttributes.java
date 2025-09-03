package com.example.test.service;

import com.example.test.controller.error.ApiError;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public class CountriesErrorAttributes extends DefaultErrorAttributes {

    private final String apiVersion;

    public CountriesErrorAttributes(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> defaultMap = super.getErrorAttributes(webRequest, options);
        ApiError apiError = ApiError.fromAttributesMap(apiVersion, defaultMap);
        return apiError.toAttributesMap();
    }
}

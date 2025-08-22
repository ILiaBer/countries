package com.example.photocatalog.config;

import com.example.photocatalog.service.CountriesErrorAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class AppConfig {

    @Value("${api.version}")
    private String apiVersion;

    @Bean
    public ErrorAttributes errorAttributes() {
        return new CountriesErrorAttributes(apiVersion);
    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
//        return objectMapper;
//    }
}

package com.example.test.config;

import com.example.test.service.CountriesErrorAttributes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

package com.example.photocatalog.service;

import com.example.photocatalog.controller.CountriesController;
import com.example.photocatalog.controller.error.ApiError;
import com.example.photocatalog.ex.CountryNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${api.version}")
    private String apiVersion;

    private Logger logger = LoggerFactory.getLogger(CountriesController.class);

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ApiError> handlerCountryNotFoundException(CountryNotFoundException ex, HttpServletRequest request) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(
                new ApiError(
                        apiVersion,
                        HttpStatus.NOT_FOUND.toString(),
                        "Photo not found",
                        request.getRequestURI(),
                        ex.getMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }
}

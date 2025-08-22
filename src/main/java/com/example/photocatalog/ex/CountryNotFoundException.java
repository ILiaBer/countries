package com.example.photocatalog.ex;

public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException() {
    }

    public CountryNotFoundException(String message) {
        super(message);
    }
}

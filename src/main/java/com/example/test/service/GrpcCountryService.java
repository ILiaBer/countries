package com.example.test.service;

import com.example.test.domain.graphql.CountryGql;
import com.example.test.domain.graphql.CountryInputGql;
import guru.qa.grpc.countries.*;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static guru.qa.grpc.countries.CountriesServiceGrpc.CountriesServiceImplBase;

@Service
public class GrpcCountryService extends CountriesServiceImplBase {

    private final CountriesService service;
    private static final Random random = new Random();


    public GrpcCountryService(CountriesService service) {
        this.service = service;
    }

    @Override
    public void country(IdRequest request, StreamObserver<CountriesResponse> responseObserver) {
        CountryGql countryGql = service.getCountryByGqlId(UUID.fromString(request.getId()));

        responseObserver.onNext(
                CountriesResponse.newBuilder()
                        .setId(countryGql.id().toString())
                        .setName(countryGql.name())
                        .setCode(countryGql.code())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void addCountry(CountryRequest request, StreamObserver<CountriesResponse> responseObserver) {
        service.addCountry(new CountryInputGql(
                        request.getName(),
                        request.getCode()
                )
        );
        responseObserver.onCompleted();
    }

    @Override
    public void randomCountry(CountRequest request, StreamObserver<CountriesResponse> responseObserver) {
        List<CountryGql> countriesGql = service.allGqlCountries();
        for (int i = 0; i < request.getCount(); i++) {
            CountryGql countryGql = countriesGql.get(random.nextInt(countriesGql.size()));
            responseObserver.onNext(
                    CountriesResponse.newBuilder()
                            .setId(countryGql.id().toString())
                            .setName(countryGql.name())
                            .setCode(countryGql.code())
                            .build()
            );
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<CountryRequest> addCountries(StreamObserver<CountResponse> responseObserver) {
        return new StreamObserver<>() {
            private final AtomicInteger counter = new AtomicInteger(0);

            @Override
            public void onNext(CountryRequest request) {
                try {
                    if (request.getCode().isBlank() || request.getName().isBlank()) {
                        throw new IllegalArgumentException("Blank fields");
                    }
                    service.addCountry(
                            new CountryInputGql(request.getCode(), request.getName())
                    );
                    counter.incrementAndGet();
                } catch (Exception e) {
                    System.err.println("Error adding country: " + e.getMessage());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error in client stream: " + throwable.getMessage());
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(
                        CountResponse.newBuilder()
                                .setCount(counter.get())
                                .build()
                );
                responseObserver.onCompleted();
            }
        };
    }
}


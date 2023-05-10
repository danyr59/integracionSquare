package com.square.examples;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.squareup.square.*;
import com.squareup.square.api.*;
import com.squareup.square.models.*;
import com.squareup.square.models.Error;
import com.squareup.square.exceptions.*;

public class Quickstart {

    public static void main(String[] args) {

        InputStream inputStream
                = Quickstart.class.getResourceAsStream("/config.properties");
        Properties prop = new Properties();

        try {
            prop.load(inputStream);
        } catch (IOException e) {
            System.out.println("Error reading properties file");
            e.printStackTrace();
        }

        SquareClient client = new SquareClient.Builder()
                .accessToken(prop.getProperty("SQUARE_ACCESS_TOKEN"))
                .environment(Environment.PRODUCTION)
                .build();

        LocationsApi locationsApi = client.getLocationsApi();
        CustomersApi customersApi = client.getCustomersApi();

        customersApi.listCustomersAsync(null, null, null, null).thenAccept(result -> {
            System.out.println("List of customers: " + result);
        }).exceptionally(exception -> {
            exception.printStackTrace();
            return null;
        });

        CreateCustomerRequest body = new CreateCustomerRequest.Builder()
                .givenName("Amelia")
                .familyName("Earhart")
                .emailAddress("Amelia.Earhart@example.com")
                .address(new Address.Builder()
                        .addressLine1("500 Electric Ave")
                        .addressLine2("Suite 600")
                        .locality("New York")
                        .administrativeDistrictLevel1("NY")
                        .postalCode("10003")
                        .country("US")
                        .build())
                .phoneNumber("+1-212-555-4240")
                .referenceId("YOUR_REFERENCE_ID")
                .note("a customer")
                .build();

        customersApi.createCustomerAsync(body).thenAccept(result -> {
            // TODO success callback handler
            System.out.println(result);
        }).exceptionally(exception -> {
            // TODO failure callback handler
            exception.printStackTrace();
            return null;
        });
        
        locationsApi.listLocationsAsync().thenAccept(result -> {
            System.out.println("Location(s) for this account:");
            for (Location l : result.getLocations()) {
                System.out.println(l);
                //l.getAddress().getAddressLine1(),
                // l.getAddress().getLocality());
            }

        }).exceptionally(exception -> {
            try {
                throw exception.getCause();
            } catch (ApiException ae) {
                for (Error err : ae.getErrors()) {
                    System.out.println(err.getCategory());
                    System.out.println(err.getCode());
                    System.out.println(err.getDetail());
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return null;
        }).join();

        SquareClient.shutdown();
    }
}

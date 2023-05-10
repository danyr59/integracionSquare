package com.square.examples;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.squareup.square.*;
import com.squareup.square.api.*;
import com.squareup.square.models.*;
import com.squareup.square.models.Error;
import com.squareup.square.exceptions.*;
import com.helpers.Customer;

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
        Customer c = new Customer("fernando", "rangel", "daarhart@example.com",
                "500 Electric Ave", "Suite 600", "New York", "NY", "10003", "US",
                "+1-212-555-4241", "YOUR_REFERENCE_ID", "a customer", customersApi);
        c.create_customer_builder();
        c.create_customer();
        c.list_of_customers();
      
/*
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

*/
        SquareClient.shutdown();
    }
}

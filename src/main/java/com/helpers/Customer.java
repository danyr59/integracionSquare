package com.helpers;

import com.squareup.square.api.CustomersApi;
import com.squareup.square.models.Address;
import com.squareup.square.models.CreateCustomerRequest;

public class Customer {

    String given_name;
    String family_name;
    String emailAddress;
    //Address address;
    String address_line_1;
    String address_line_2;
    String locality;
    String administrative_district_level_1;
    String postal_code;
    String country;
    String phone_number;
    String reference_id;
    String note;
    private CreateCustomerRequest body;
    CustomersApi customers_api;

    public Customer(String gn, String fn, String ea,
            String al1, String al2,
            String l, String adl, String pc, String c,
            String pn, String rid, String n, CustomersApi customer_api) {

        this.given_name = gn;
        this.family_name = fn;
        this.emailAddress = ea;
        this.address_line_1 = al1;
        this.address_line_2 = al2;
        this.locality = l;
        this.administrative_district_level_1 = adl;
        this.postal_code = pc;
        this.country = c;
        this.phone_number = pn;
        this.reference_id = rid;
        this.note = n;
        this.customers_api = customer_api;

    }

    public void create_customer_builder() {
        this.body = new CreateCustomerRequest.Builder()
                .givenName(this.given_name)
                .familyName(this.family_name)
                .emailAddress(this.emailAddress)
                .address(new Address.Builder()
                        .addressLine1(this.address_line_1)
                        .addressLine2(this.address_line_2)
                        .locality(this.locality)
                        .administrativeDistrictLevel1(this.administrative_district_level_1)
                        .postalCode(this.postal_code)
                        .country(this.country)
                        .build())
                .phoneNumber(this.phone_number)
                .referenceId(this.reference_id)
                .note(this.note)
                .build();

    }

    public void create_customer() {
        this.customers_api.createCustomerAsync(body)
                .thenAccept(result -> {
                    System.out.printf("customer created:\n Given name = %s Family name = %s \n",
                            result.getCustomer().getGivenName(),
                            result.getCustomer().getFamilyName());
                })
                .exceptionally(exception -> {
                    // Your error-handling code here
                    return null;
                })
                .join();
    }

    public void list_of_customers() {
        this.customers_api.listCustomersAsync(null, null, null, null).thenAccept(result -> {
            System.out.println("List of customers: " + result);
        }).exceptionally(exception -> {
            System.out.println("exceptcion");
            exception.printStackTrace();
            return null;
        }).join();
    }

}


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

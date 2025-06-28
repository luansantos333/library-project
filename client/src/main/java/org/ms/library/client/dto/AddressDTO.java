package org.ms.library.client.dto;

import jakarta.persistence.Column;
import org.ms.library.client.entity.Address;

import java.util.UUID;

public class AddressDTO {

    private Long id;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;

    public AddressDTO(Address entity) {


        this.id = entity.getId();
        this.address = entity.getAddress();
        this.city = entity.getCity();
        this.state = entity.getState();
        this.zip = entity.getZip();
        this.country = entity.getCountry();

    }


    public AddressDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }


}

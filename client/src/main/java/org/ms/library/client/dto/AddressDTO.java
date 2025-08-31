package org.ms.library.client.dto;

import jakarta.validation.constraints.NotBlank;
import org.ms.library.client.entity.Address;
import org.ms.library.client.utils.ValidCEP;

public class AddressDTO {

    private Long id;
    @NotBlank (message = "You cannot leave this field empty!")
    private String address;
    @NotBlank (message = "You cannot leave this field empty!")
    private String city;
    @NotBlank (message = "You cannot leave this field empty!")
    private String state;
    @ValidCEP
    private String zip;
    @NotBlank (message = "You cannot leave this field empty!")
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

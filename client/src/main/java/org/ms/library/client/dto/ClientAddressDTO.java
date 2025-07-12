package org.ms.library.client.dto;

import org.ms.library.client.entity.Client;

public class ClientAddressDTO {
    private Long id;
    private String name;
    private String lastName;
    private String cpf;
    private String phone;
    private AddressDTO addressDTO;


public ClientAddressDTO (Client entity) {

    this.id = entity.getId();
    this.name = entity.getName();
    this.lastName = entity.getLastName();
    this.cpf = entity.getCpf();
    this.phone = entity.getPhone();

    this.addressDTO = new AddressDTO(entity.getAddress());

}

    public ClientAddressDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPhone() {
        return phone;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }
}

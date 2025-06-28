package org.ms.library.client.dto;

import org.ms.library.client.entity.Client;

public class ClientAddressUserDTO {

    private Long id;
    private String name;
    private String lastName;
    private String cpf;
    private String phone;
    private AddressDTO address;
    private UserDTO user;

    public ClientAddressUserDTO(Client entity, UserDTO user) {

        this.id = entity.getId();
        this.name = entity.getName();
        this.lastName = entity.getLastName();
        this.cpf = entity.getCpf();
        this.phone = entity.getPhone();
        this.address = new AddressDTO(entity.getAddress());
        this.user = user;

    }


    public ClientAddressUserDTO() {
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

    public AddressDTO getAddress() {
        return address;
    }

    public UserDTO getUser() {
        return user;
    }
}

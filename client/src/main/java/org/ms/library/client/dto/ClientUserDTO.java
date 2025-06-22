package org.ms.library.client.dto;

import org.ms.library.client.entity.Client;

public class ClientUserDTO {

    private Long id;
    private String name;
    private String lastName;
    private String cpf;
    private String phone;
    private AddressDTO address;
    private UserCompleteDTO user;

    public ClientUserDTO(Client entity, UserCompleteDTO user) {

        this.id = entity.getId();
        this.name = entity.getName();
        this.lastName = entity.getLastName();
        this.cpf = entity.getCpf();
        this.phone = entity.getPhone();
        this.address = new AddressDTO(entity.getAddress());
        this.user = new UserCompleteDTO();

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

    public UserCompleteDTO getUser() {
        return user;
    }
}

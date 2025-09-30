package org.ms.library.client.dto;

import org.ms.library.client.entity.Client;

import java.util.UUID;

public class ClientDTO {
    private Long id;
    private String name;
    private String lastName;
    private String cpf;
    private String phone;
    private UUID user_id;

    public ClientDTO(Client client) {

        this.id = client.getId();
        this.name = client.getName();
        this.lastName = client.getLastName();
        this.cpf = client.getCpf();
        this.phone = client.getPhone();
        this.user_id = client.getUser_id();


    }

    public ClientDTO() {
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

    public UUID getUser_id() {
        return user_id;
    }
}

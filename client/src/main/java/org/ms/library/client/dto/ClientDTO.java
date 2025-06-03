package org.ms.library.client.dto;

import org.ms.library.client.entity.Client;

public class ClientDTO {
    private Long id;
    private String name;
    private String lastName;
    private String cpf;
    private String phone;

    public ClientDTO(Client client) {

        this.id = client.getId();
        this.name = client.getName();
        this.lastName = client.getLastName();
        this.cpf = client.getCpf();
        this.phone = client.getPhone();


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
}

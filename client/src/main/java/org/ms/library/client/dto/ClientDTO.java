package org.ms.library.client.dto;

import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;
import org.ms.library.client.entity.Client;
import org.ms.library.client.utils.ValidCPF;

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
}

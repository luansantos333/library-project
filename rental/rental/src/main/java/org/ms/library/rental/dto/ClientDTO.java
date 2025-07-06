package org.ms.library.rental.dto;

public class ClientDTO {
    private Long id;
    private String name;
    private String lastName;
    private String cpf;
    private String phone;

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

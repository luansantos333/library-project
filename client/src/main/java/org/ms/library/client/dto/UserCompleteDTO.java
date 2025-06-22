package org.ms.library.client.dto;

public class UserCompleteDTO {
    private String username;
    private String password;
    private static final String DEFAULT_ROLE = "ROLE_USER";

    public UserCompleteDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public UserCompleteDTO() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

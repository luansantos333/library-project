package org.ms.library.user.dto;

import org.ms.library.user.entity.Role;
import org.ms.library.user.entity.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserDTO {

    private UUID id;
    private String username;
    private Set<RoleDTO> roles = new HashSet<>();
    private String password;


    public UserDTO(User entity) {

        this.id = entity.getId();
        this.username = entity.getUsername();
        this.password = entity.getPassword();

        for (Role role : entity.getRoles()) {

            roles.add(new RoleDTO(role));

        }

    }

    public UserDTO(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public UUID getId() {
        return id;
    }
}

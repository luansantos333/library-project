package org.ms.library.authentication.dto;

import org.ms.library.authentication.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {


    private String username;
    private Set<RoleDTO> roles = new HashSet<>();
    private String password;


    public UserDTO() {
    }

    public UserDTO(User entity) {

        this.username = entity.getUsername();
        this.password = entity.getPassword();

        for (GrantedAuthority role : entity.getAuthorities()) {

            roles.add(new RoleDTO((Role) role));

        }

    }


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

}

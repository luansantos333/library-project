package org.ms.library.authentication.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class Role implements GrantedAuthority {
    private Long id;
    private String authority;
    private String description;
    private List<User> users = new ArrayList<User>();

    public Role(Long id, String authority, String description, List<User> users) {
        this.id = id;
        this.authority = authority;
        this.description = description;
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

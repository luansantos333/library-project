package org.ms.library.user.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity (name = "tb_role")
public class Role  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;
    private String description;
    @ManyToMany
    List<User> users = new ArrayList<User>();

    public Role(Long id, String authority, String description, List<User> users) {
        this.id = id;
        this.authority = authority;
        this.description = description;
        this.users = users;
    }

    public Role() {

    }

    public Role(Long id, String authority, String description) {
        this.id = id;
        this.authority = authority;
        this.description = description;
    }

    public String getAuthority() {
        return authority;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthority(String name) {
        this.authority = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }
}

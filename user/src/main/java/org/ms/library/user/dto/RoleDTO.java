package org.ms.library.user.dto;

import org.ms.library.user.entity.Role;

public class RoleDTO {

    private Long id;
    private String name;
    private String description;

    public RoleDTO(Role entity) {

        this.id = entity.getId();
        this.name = entity.getAuthority();
        this.description = entity.getDescription();

    }


    public RoleDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

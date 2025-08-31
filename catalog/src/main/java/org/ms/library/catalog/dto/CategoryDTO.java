package org.ms.library.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import org.ms.library.catalog.entity.Category;

public class CategoryDTO {

    private Long id;
    @NotBlank (message = "You cannot leave this field empty")
    private String name;

    public CategoryDTO(Category entity) {

        this.id = entity.getId();
        this.name = entity.getName();

    }

    public CategoryDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

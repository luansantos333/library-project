package org.ms.library.catalog.dto;

import org.ms.library.catalog.entity.Category;

public class CategoryDTO {

    private Long id;
    private String name;

    public CategoryDTO(Category entity) {

        this.id = entity.getId();
        this.name = entity.getName();

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

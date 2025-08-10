package org.ms.library.rental.dto;

import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

public class BookCategoriesDTO {

    private Long id;
    private String title;
    private String author;
    private Double price;
    private Set<CategoryDTO> categories = new HashSet<>();
    private Set<Long> categories_ids = new HashSet<>();
    private Integer quantity;


    public BookCategoriesDTO() {
    }

    public BookCategoriesDTO(Long id, String title, String author, Double price, Set<CategoryDTO> categories, Set<Long> categories_ids, Integer quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.categories = categories;
        this.categories_ids = categories_ids;
        this.quantity = quantity;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public Set<Long> getCategories_ids() {
        return categories_ids;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

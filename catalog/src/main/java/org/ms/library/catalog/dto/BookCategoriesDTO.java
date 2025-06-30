package org.ms.library.catalog.dto;

import org.ms.library.catalog.entity.Book;
import org.ms.library.catalog.entity.Category;

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

    public BookCategoriesDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.price = book.getPrice();
        this.quantity = book.getQuantity();
        for (Category entity : book.getCategories()) {
            this.categories.add(new CategoryDTO(entity));
        }


    }

    public BookCategoriesDTO() {
    }


    public Integer getQuantity() {
        return quantity;
    }

    public Set<Long> getCategories_ids() {
        return categories_ids;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Double getPrice() {
        return price;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }
}

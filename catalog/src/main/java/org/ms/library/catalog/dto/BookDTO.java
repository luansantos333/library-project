package org.ms.library.catalog.dto;

import org.ms.library.catalog.entity.Book;

public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private Double price;

    public BookDTO(Book entity) {

        this.id = entity.getId();
        this.title =entity.getTitle();
        this.author = entity.getAuthor();
        this.price = entity.getPrice();

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
}

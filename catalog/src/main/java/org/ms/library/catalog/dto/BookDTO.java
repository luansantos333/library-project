package org.ms.library.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.ms.library.catalog.entity.Book;

public class BookDTO {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank (message = "You cannot leave this field empty")
    private String author;
    @PositiveOrZero (message = "You cannot enter a number lower than 0")
    @NotBlank (message = "You cannot leave this field empty")
    private Double price;
    @PositiveOrZero (message = "You cannot enter a number lower than 0")
    @NotBlank (message = "You cannot leave this field empty")
    private Integer quantity;

    public BookDTO(Book entity) {

        this.id = entity.getId();
        this.title =entity.getTitle();
        this.author = entity.getAuthor();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity();

    }

    public Integer getQuantity() {
        return quantity;
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

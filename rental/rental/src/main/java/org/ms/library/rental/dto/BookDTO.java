package org.ms.library.rental.dto;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer quantity;

    public BookDTO() {
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

    public Integer getQuantity() {
        return quantity;
    }
}

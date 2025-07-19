package org.ms.library.rental.dto;

import org.ms.library.rental.entities.RentalItem;

public class RentalItemDTO {

    private Long bookId;
    private String title;
    private String author;
    private Double price;

    public RentalItemDTO(RentalItem rentalItem, BookDTO bookDetails) {
        this.bookId = rentalItem.getBookId();
        this.title = bookDetails.getTitle();
        this.author = bookDetails.getAuthor();
        this.price = bookDetails.getPrice();
    }


    public RentalItemDTO() {
    }

    public Long getBookId() {
        return bookId;
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

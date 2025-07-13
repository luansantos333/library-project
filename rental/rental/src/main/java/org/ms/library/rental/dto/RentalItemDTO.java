package org.ms.library.rental.dto;

import org.ms.library.rental.entities.RentalItem;

public class RentalItemDTO {

    private Long id;
    private Long bookId;

    public RentalItemDTO(RentalItem rentalItem) {

        this.id = rentalItem.getId();
        this.bookId = rentalItem.getBookId();

    }

    public RentalItemDTO() {
    }

    public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }
}

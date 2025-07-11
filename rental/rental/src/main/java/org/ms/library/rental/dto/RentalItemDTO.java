package org.ms.library.rental.dto;

import org.ms.library.rental.entities.Rental;
import org.ms.library.rental.entities.RentalItem;

public class RentalItemDTO {

    private Long id;
    private Rental rental;
    private Long bookId;

    public RentalItemDTO(RentalItem rentalItem) {

        this.id = rentalItem.getId();
        this.rental = rentalItem.getRental();
        this.bookId = rentalItem.getBookId();

    }

    public RentalItemDTO() {
    }

    public Long getId() {
        return id;
    }

    public Rental getRental() {
        return rental;
    }

    public Long getBookId() {
        return bookId;
    }
}

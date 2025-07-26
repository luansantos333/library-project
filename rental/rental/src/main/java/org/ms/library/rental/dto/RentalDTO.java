package org.ms.library.rental.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.ms.library.rental.entities.Rental;
import org.ms.library.rental.entities.RentalItem;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RentalDTO {

    private UUID id;
    private LocalDateTime rentalDate;
    @NotNull (message = "Client id required")
    private Long clientId;
    @PastOrPresent (message = "Return date needs to be today or before")
    private LocalDateTime returnDate;
    private LocalDateTime dueDate;
    @NotNull (message = "You need to rent atleast one book to call this service")
    private Set<RentalItemDTO> items = new HashSet<>();
    private Double total;

    public RentalDTO(Rental entity, Map<Long, BookCategoriesDTO> bookDetailsMap) {

        this.id = entity.getId();
        this.rentalDate = entity.getRentalDate();
        this.clientId = entity.getClientId();
        this.dueDate = entity.getDueDate();
        this.total = 0.0;

        for (RentalItem item : entity.getItems()) {
            BookCategoriesDTO bookDetails = bookDetailsMap.get(item.getBookId());
            if (bookDetails != null) {
                items.add(new RentalItemDTO(item, bookDetails));
                this.total += (bookDetails.getPrice() * item.getQuantity());
            }
        }
    }


    public RentalDTO(UUID id, LocalDateTime rentalDate, Long clientId, LocalDateTime returnDate, LocalDateTime dueDate, Set<RentalItemDTO> items, Double total) {
        this.id = id;
        this.rentalDate = rentalDate;
        this.clientId = clientId;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.items = items;
        this.total = total;
    }


    public RentalDTO(Rental entity, Map<Long, BookCategoriesDTO> bookDetailsMap, LocalDateTime returnDate) {

        this.id = entity.getId();
        this.rentalDate = entity.getRentalDate();
        this.clientId = entity.getClientId();
        this.dueDate = entity.getDueDate();
        this.total = 0.0;
        this.returnDate = returnDate;

        for (RentalItem item : entity.getItems()) {
            BookCategoriesDTO bookDetails = bookDetailsMap.get(item.getBookId());
            if (bookDetails != null) {
                items.add(new RentalItemDTO(item, bookDetails));
                this.total += bookDetails.getPrice();
            }
        }
    }





    public RentalDTO() {
    }




    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public Set<RentalItemDTO> getItems() {
        return items;
    }

    public Double getTotal() {
        return total;
    }
}

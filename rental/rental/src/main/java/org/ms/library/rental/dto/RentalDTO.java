package org.ms.library.rental.dto;

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
    private Long clientId;
    private LocalDateTime dueDate;
    private Set<RentalItemDTO> items = new HashSet<>();
    private Double total;

    public RentalDTO(Rental entity, Map<Long, BookDTO> bookDetailsMap) {

        this.id = entity.getId();
        this.rentalDate = entity.getRentalDate();
        this.clientId = entity.getClientId();
        this.dueDate = entity.getDueDate();
        this.total = 0.0;

        for (RentalItem item : entity.getItems()) {
            BookDTO bookDetails = bookDetailsMap.get(item.getBookId());
            if (bookDetails != null) {
                items.add(new RentalItemDTO(item, bookDetails));
                this.total += bookDetails.getPrice();
            }
        }
    }


    public RentalDTO() {
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

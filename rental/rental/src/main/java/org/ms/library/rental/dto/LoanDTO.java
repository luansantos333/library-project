package org.ms.library.rental.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.ms.library.rental.entities.Loan;
import org.ms.library.rental.entities.LoanItem;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LoanDTO {

    private UUID id;
    private LocalDateTime loanDate;
    @NotNull (message = "Client id required")
    private Long clientId;
    @PastOrPresent (message = "Return date needs to be today or before")
    private LocalDateTime returnDate;
    private LocalDateTime dueDate;
    @NotNull (message = "You need to rent atleast one book to call this service")
    private Set<LoanItemDTO> items = new HashSet<>();
    private Double total;

    public LoanDTO(Loan entity, Map<Long, BookCategoriesDTO> bookDetailsMap) {

        this.id = entity.getId();
        this.loanDate = entity.getLoanDate();
        this.clientId = entity.getClientId();
        this.dueDate = entity.getDueDate();
        this.total = 0.0;

        for (LoanItem item : entity.getItems()) {
            BookCategoriesDTO bookDetails = bookDetailsMap.get(item.getBookId());
            if (bookDetails != null) {
                items.add(new LoanItemDTO(item, bookDetails));
                this.total += (bookDetails.getPrice() * item.getQuantity());
            }
        }
    }


    public LoanDTO(UUID id, LocalDateTime loanDate, Long clientId, LocalDateTime returnDate, LocalDateTime dueDate, Set<LoanItemDTO> items, Double total) {
        this.id = id;
        this.loanDate = loanDate;
        this.clientId = clientId;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.items = items;
        this.total = total;
    }


    public LoanDTO(Loan entity, Map<Long, BookCategoriesDTO> bookDetailsMap, LocalDateTime returnDate) {

        this.id = entity.getId();
        this.loanDate = entity.getLoanDate();
        this.clientId = entity.getClientId();
        this.dueDate = entity.getDueDate();
        this.total = 0.0;
        this.returnDate = returnDate;

        for (LoanItem item : entity.getItems()) {
            BookCategoriesDTO bookDetails = bookDetailsMap.get(item.getBookId());
            if (bookDetails != null) {
                items.add(new LoanItemDTO(item, bookDetails));
                this.total += bookDetails.getPrice();
            }
        }
    }





    public LoanDTO() {
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

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public Set<LoanItemDTO> getItems() {
        return items;
    }

    public Double getTotal() {
        return total;
    }
}

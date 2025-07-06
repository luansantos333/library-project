package org.ms.library.rental.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity (name = "tb_rental")
public class Rental {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID id;
    @Column (nullable = false)
    private LocalDateTime rentalDate;
    @Column (nullable = false)
    private UUID clientId;
    private LocalDateTime returnDate;
    @Column (nullable = false)
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    private RentalStatus status;
    @OneToMany
    private Set<RentalItem> items = new HashSet<>();


    public Rental(UUID id, LocalDateTime rentalDate, UUID clientId, LocalDateTime returnDate, LocalDateTime dueDate, RentalStatus status, Set<RentalItem> items) {
        this.id = id;
        this.rentalDate = rentalDate;
        this.clientId = clientId;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.status = status;
        this.items = items;
    }

    public Rental() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public Set<RentalItem> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return Objects.equals(id, rental.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

enum RentalStatus {

    ACTIVE,
    RETURNED,
    LATE

}

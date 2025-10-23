package org.ms.library.rental.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private LocalDateTime loanDate;
    @Column(nullable = false)
    private Long clientId;
    private LocalDateTime returnDate;
    @Column(nullable = false)
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LoanItem> items = new HashSet<>();
    private Double total;


    public Loan(UUID id, LocalDateTime loanDate, Long clientId, LocalDateTime returnDate, LocalDateTime dueDate, LoanStatus status, Set<LoanItem> items) {
        this.id = id;
        this.loanDate = loanDate;
        this.clientId = clientId;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.status = status;
        this.items = items;
    }


    public Loan(UUID id, LocalDateTime loanDate, Long clientId, LocalDateTime returnDate, LocalDateTime dueDate, LoanStatus status, Set<LoanItem> items, Double total) {
        this.id = id;
        this.loanDate = loanDate;
        this.clientId = clientId;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.status = status;
        this.items = items;
        this.total = total;
    }

    public Loan() {
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime rentalDate) {
        this.loanDate = rentalDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
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

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Set<LoanItem> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}


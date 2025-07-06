package org.ms.library.rental.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity (name = "tb_rental_itens")
public class RentalItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn (name = "rental_id", nullable = false)
    private Rental rental;
    @Column(nullable = false)
    private Long bookId;


    public RentalItem(Long id, Rental rental, Long bookId) {
        this.id = id;
        this.rental = rental;
        this.bookId = bookId;
    }

    public RentalItem() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RentalItem that = (RentalItem) o;
        return Objects.equals(id, that.id) && Objects.equals(rental, that.rental);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rental);
    }
}

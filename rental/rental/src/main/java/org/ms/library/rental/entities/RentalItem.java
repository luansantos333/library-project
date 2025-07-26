package org.ms.library.rental.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "tb_rental_itens")
public class RentalItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long bookId;
    private Double price;
    private Integer quantity;

    public RentalItem(Long id, Rental rental, Long bookId) {
        this.id = id;
        this.bookId = bookId;
    }

    public RentalItem(Long bookId, Double price, Integer quantity) {
        this.bookId = bookId;
        this.price = price;
        this.quantity = quantity;
    }

    public RentalItem() {
    }

    public RentalItem(Long id, Long bookId, Double price) {
        this.id = id;
        this.bookId = bookId;
        this.price = price;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(id, that.id) && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId);
    }
}

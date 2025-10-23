package org.ms.library.rental.dto;

import org.ms.library.rental.entities.LoanItem;

import java.util.HashSet;
import java.util.Set;

public class LoanItemDTO {

    private Long bookId;
    private String title;
    private String author;
    private Double price;
    private Integer quantity;
    private final Set<CategoryDTO> categories = new HashSet<>();

    public LoanItemDTO(LoanItem loanItem, BookCategoriesDTO bookDetails) {
        this.bookId = loanItem.getBookId();
        this.title = bookDetails.getTitle();
        this.author = bookDetails.getAuthor();
        this.price = bookDetails.getPrice();
        this.quantity = loanItem.getQuantity();

        for (CategoryDTO category : bookDetails.getCategories()) {

            categories.add(new CategoryDTO(category.getId(), category.getName()));

        }

    }

    public LoanItemDTO() {
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
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

    public Integer getQuantity() {
        return quantity;
    }
}

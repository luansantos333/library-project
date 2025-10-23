package org.ms.library.rental.dto;

import org.ms.library.rental.entities.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RentalsBookCategoriesClientDTO {

    private String clientName;
    private String clientLastName;
    private String ClientCpf;
    private String clientPhone;
    private final List<LoanDTO> rentals = new ArrayList<>();
    private Double total;


    public RentalsBookCategoriesClientDTO(String clientName, String clientLastName, String clientCpf, String clientPhone, List<Loan> entity, Map<Long, BookCategoriesDTO> bookDetailsMap) {
        this.clientName = clientName;
        this.clientLastName = clientLastName;
        this.ClientCpf = clientCpf;
        this.clientPhone = clientPhone;
        this.total = 0.0;
        for (Loan loan : entity) {
            LoanDTO loanDTO = new LoanDTO(loan, bookDetailsMap);
            this.rentals.add(loanDTO);
            this.total += loanDTO.getTotal();
        }


    }


    public RentalsBookCategoriesClientDTO() {
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public String getClientCpf() {
        return ClientCpf;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public List<LoanDTO> getRentals() {
        return rentals;
    }

    public Double getTotal() {
        return total;
    }
}

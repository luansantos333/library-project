package org.ms.library.rental.dto;

import org.ms.library.rental.entities.Rental;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RentalsBookClientDTO {

    private String clientName;
    private String clientLastName;
    private String ClientCpf;
    private String clientPhone;
    private List<RentalDTO> rentals = new ArrayList<>();
    private Double total;


    public RentalsBookClientDTO(String clientName, String clientLastName, String clientCpf, String clientPhone, List<Rental> entity, Map<Long, BookDTO> bookDetailsMap) {
        this.clientName = clientName;
        this.clientLastName = clientLastName;
        this.ClientCpf = clientCpf;
        this.clientPhone = clientPhone;
        this.total = 0.0;
        for (Rental rental : entity) {
            RentalDTO rentalDTO = new RentalDTO(rental, bookDetailsMap);
            this.rentals.add(rentalDTO);
            this.total += rentalDTO.getTotal();
        }


    }


    public RentalsBookClientDTO() {
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

    public List<RentalDTO> getRentals() {
        return rentals;
    }

    public Double getTotal() {
        return total;
    }
}

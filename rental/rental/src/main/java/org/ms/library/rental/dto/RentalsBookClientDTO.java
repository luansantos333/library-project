package org.ms.library.rental.dto;

import org.ms.library.rental.entities.Rental;

import java.util.ArrayList;
import java.util.List;

public class RentalsBookClientDTO {

    private String clientName;
    private String clientLastName;
    private String ClientCpf;
    private String clientPhone;
    private List<RentalDTO> rentals = new ArrayList<>();
    private Long total;


    public RentalsBookClientDTO(String clientName, String clientLastName, String clientCpf, String clientPhone, List<Rental> entity) {
        this.clientName = clientName;
        this.clientLastName = clientLastName;
        ClientCpf = clientCpf;
        this.clientPhone = clientPhone;
        for (Rental rental : entity) {


            this.rentals.add(new RentalDTO(rental));
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

    public Long getTotal() {
        return total;
    }
}
